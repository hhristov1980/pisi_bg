
package pisibg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNullFields;
import org.springframework.web.bind.annotation.*;
import pisibg.exceptions.AuthenticationException;
import pisibg.exceptions.DeniedPermissionException;
import pisibg.exceptions.MySQLException;
//import pisibg.model.pojo.Payment;
import pisibg.model.dto.orderDTO.*;
import pisibg.model.dto.userDTO.*;
import pisibg.model.pojo.User;
import pisibg.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController extends AbstractController {
    static Logger log = Logger.getLogger(ProductController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;


    @PostMapping("/users")
    public UserRegisterResponseDTO register(@Valid @RequestBody UserRegisterRequestDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PutMapping("/users")
    public UserWithoutPassDTO login(@Valid @RequestBody UserLoginDTO dto, HttpSession ses) {
        UserWithoutPassDTO responseDto = userService.login(dto);
        sessionManager.loginUser(ses, responseDto.getId());
        return responseDto;
    }

    @PostMapping("/logout")
    public void logout(HttpSession ses) {
        sessionManager.logoutUser(ses);
    }


    @PutMapping("/users/edit")
    public UserEditResponseDTO editUser(@Valid @RequestBody UserEditRequestDTO userDto, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            int id = user.getId();
            return userService.edit(userDto, id);
        }
    }

    @PostMapping("/users/edit")
    public String editPasswordUser(@Valid @RequestBody UserEditPasswordDTO userDto, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            int id = user.getId();
            return userService.editPassword(userDto, id);
        }
    }

    @PostMapping("/admin/{id_user}")
    public UserRegisterResponseDTO makeAdmin(@PathVariable int id_user, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            int admin_id = user.getId();
            return userService.makeAdmin(admin_id, id_user);
        }
    }

    @PutMapping("/admin/{id_user}")
    public UserRegisterResponseDTO removeAdmin(@PathVariable int id_user, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            int admin_id = user.getId();
            return userService.removeAdmin(admin_id, id_user);
        }
    }

    @DeleteMapping("/admin/{user_id}")
    public UserEditResponseDTO deleteUser(@PathVariable int user_id, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            int admin_id = user.getId();
            try {
                return userService.deleteUser(admin_id, user_id);
            } catch (SQLException throwables) {
                log.log(Level.ALL,throwables.getMessage());
                throw new MySQLException("Something get wrong!");
            }
        }
    }

    @PostMapping("/admin/users")
    public List<UserRegisterResponseDTO> getAllUsers(@Valid @RequestBody UserReportRequestDTO dto, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            try {
                return userService.getAllUsers(dto);
            } catch (SQLException throwables) {
                log.log(Level.ALL,throwables.getMessage());
                throw new MySQLException("Something get wrong!");
            }
        }
    }


    @PostMapping("/admin/daily")
    public List<OrderReportDTO> getDailyOrders(@Valid @RequestBody OrderDailyReportRequestDTO dto, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            try {
                LocalDateTime from = dto.getFromDate();
                LocalDateTime to = dto.getToDate();
                return userService.getDailyOrders(from, to, dto);
            } catch (SQLException throwables) {
                log.log(Level.ALL,throwables.getMessage());
                throw new MySQLException("Something get wrong!");
            }
        }
    }

    @PostMapping("/admin/monthly")
    public List<OrderReportDTO> getMonthlyOrders(@Valid @RequestBody OrderMonthlyReportRequestDTO dto, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            try {
                return userService.getMonhlyOrders(dto);
            } catch (SQLException throwables) {
                log.log(Level.ALL,throwables.getMessage());
                throw new MySQLException("Something get wrong!");
            }
        }
    }

    @PostMapping("/admin/yearly")
    public List<OrderReportDTO> getYearlyOrders(@Valid @RequestBody OrderYearlyReportRequestDTO dto, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            try {
                return userService.getYearlyOrders(dto);
            } catch (SQLException throwables) {
                log.log(Level.ALL,throwables.getMessage());
                throw new MySQLException("Something get wrong!");
            }
        }
    }

    @PutMapping("/users/order/{order_id}")
    public OrderEditResponseDTO editOrder(@PathVariable int order_id,@Valid @RequestBody OrderEditRequestDTO orderDto, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (!user.isAdmin()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            int admin_id = user.getId();
            return userService.editOrder(admin_id, order_id, orderDto);
        }
    }

    @GetMapping("/users/{id}")
    public UserWithoutPassDTO getById(@PathVariable int id, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (id != user.getId()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            return userService.getById(id);
        }
    }

    @DeleteMapping("/users/{id}")
    public UserEditResponseDTO softDelete(@PathVariable int id, HttpSession ses) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        } else {
            User user = sessionManager.getLoggedUser(ses);
            if (id != user.getId()) {
                throw new DeniedPermissionException("You dont have permission for that!");
            }
            try {
                return userService.softDelete(id);
            } catch (SQLException throwables) {
                log.log(Level.ALL,throwables.getMessage());
                throw new MySQLException("Something get wrong!");
            }
        }
    }
}