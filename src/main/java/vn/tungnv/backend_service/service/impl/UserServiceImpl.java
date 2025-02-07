package vn.tungnv.backend_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.tungnv.backend_service.common.UserStatus;
import vn.tungnv.backend_service.controller.request.AddressRequest;
import vn.tungnv.backend_service.controller.request.UserCreateRequest;
import vn.tungnv.backend_service.controller.request.UserUpdateRequest;
import vn.tungnv.backend_service.controller.response.UserPageResponse;
import vn.tungnv.backend_service.controller.response.UserResponse;
import vn.tungnv.backend_service.exception.EntityExistException;
import vn.tungnv.backend_service.exception.EntityNotFoundException;
import vn.tungnv.backend_service.model.Address;
import vn.tungnv.backend_service.model.User;
import vn.tungnv.backend_service.repository.AddressRepository;
import vn.tungnv.backend_service.repository.UserRepository;
import vn.tungnv.backend_service.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "USER_SERVICE")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public UserPageResponse getAll(String keyword, String sort, int page, int size) {
        log.info("getAll user start");
        //Sort
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)"); //Moi () la 1 group. Example: Ten cot : asc/desc
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order = new Sort.Order(Sort.Direction.ASC, columName);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, columName);
                }
            }
        }

        //Xu ly truong hop FE muon bat dau voi page = 1
        int pageNo = 0;
        if (page > 0) {
            pageNo = page - 1;
        }

        //Paging
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(order));
        Page<User> userPage = null;

        if (StringUtils.hasLength(keyword)) { //Vua check null vua check blank
            //Goi phuong thuc search
            keyword = "%" +  keyword.toLowerCase() + "%";

            userPage = userRepository.searchByKeyword(keyword, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        return getUserPageResponse(page, size, userPage);
    }

    @Override
    public UserResponse findUserById(long id) {
        log.info("find user by id: {}", id);
        User user = getUserById(id);
        return UserResponse.builder()
                .id(id)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender().name())
                .birthDay(user.getDateOfBirth())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateRequest userCreateRequest) {
        User byUsername = userRepository.findByUsername(userCreateRequest.getUsername());
        if (byUsername != null) {
            throw new EntityExistException("User is exist!");
        }
        User user = new User();
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setDateOfBirth(userCreateRequest.getDateOfBirth());
        user.setGender(userCreateRequest.getGender());
        user.setPhone(userCreateRequest.getPhone());
        user.setEmail(userCreateRequest.getEmail());
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());
        user.setUserStatus(userCreateRequest.getUserStatus());
        user.setUserType(userCreateRequest.getUserType());
        User userSave = userRepository.save(user);

        if (userSave.getId() != null) {
            List<Address> addressList = new ArrayList<>();
            for (AddressRequest addressRequest : userCreateRequest.getAddressRequests()) {
                Address address = convertToEntity(addressRequest);
                address.setUserID(userSave);
                addressList.add(address);
            }
            addressRepository.saveAll(addressList);
        }
        log.info("Add successfully");
        return userSave.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateRequest updateRequest) {
        User user = getUserById(updateRequest.getId());
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setDateOfBirth(updateRequest.getDateOfBirth());
        user.setGender(updateRequest.getGender());
        user.setUserStatus(updateRequest.getUserStatus());
        user.setUserType(updateRequest.getUserType());
        User userSave = userRepository.save(user);

        if (updateRequest.getAddressRequests() != null) {
            List<Address> addressList = new ArrayList<>();
            for (AddressRequest addressRequest : updateRequest.getAddressRequests()) {
                log.info("id {} - type {}", updateRequest.getId(), addressRequest.getAddressType());
                Address address = addressRepository.findByUserIDAndType(updateRequest.getId(), addressRequest.getAddressType());
                if (address == null) {
                    address = new Address();
                }
                address.setApartmentNumber(addressRequest.getApartmentNumber());
                address.setFloor(addressRequest.getFloor());
                address.setBuilding(addressRequest.getBuilding());
                address.setStreetNumber(addressRequest.getStreetNumber());
                address.setStreet(addressRequest.getStreet());
                address.setCity(addressRequest.getCity());
                address.setCountry(addressRequest.getCountry());
                address.setAddressType(addressRequest.getAddressType());
                address.setUserID(userSave);
                addressList.add(address);
            }
            addressRepository.saveAll(addressList);
            log.info("Update successfully");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatusUser(long id, UserStatus status) {
        User user = getUserById(id);
        user.setUserStatus(status);
        userRepository.save(user);
        log.info("Change status user {}", user.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(long id) {
        User user = getUserById(id);
        List<Long> allIdsByUserID = this.addressRepository.getAllIdsByUserID(user.getId());
        this.addressRepository.deleteAllById(allIdsByUserID);
        userRepository.delete(user);
        log.info("Deleted user successfully");
    }

    private User getUserById(long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    private Address convertToEntity(AddressRequest addressRequest) {
        Address address = new Address();
        address.setApartmentNumber(addressRequest.getApartmentNumber());
        address.setFloor(addressRequest.getFloor());
        address.setBuilding(addressRequest.getBuilding());
        address.setStreetNumber(addressRequest.getStreetNumber());
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        address.setAddressType(addressRequest.getAddressType());

        return address;
    }

    /**
     * Convert entity to UserResponse
     * @param page
     * @param size
     * @param users
     * @return
     **/
    private static UserPageResponse getUserPageResponse(int page, int size, Page<User> users) {
        log.info("getUserPageResponse page");
        List<UserResponse> userList = users.stream().map(u -> UserResponse.builder()
                        .id(u.getId())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .gender(u.getGender().name())
                        .birthDay(u.getDateOfBirth())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .phone(u.getPhone())
                        .build())
                .toList();

        UserPageResponse userResponse = new UserPageResponse();
        userResponse.setPageNumber(page);
        userResponse.setPageSize(size);
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setData(userList);
        return userResponse;
    }

}
