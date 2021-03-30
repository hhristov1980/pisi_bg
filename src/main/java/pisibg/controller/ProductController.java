package pisibg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pisibg.exceptions.AuthenticationException;
import pisibg.exceptions.DeniedPermissionException;
import pisibg.model.dao.ProductDAO;
import pisibg.model.dto.*;
import pisibg.model.pojo.User;
import pisibg.model.repository.UserRepository;
import pisibg.service.ProductService;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private ProductDAO productDAO;


    @PostMapping("/products")
    public ProductResponseDTO add(HttpSession ses, @RequestBody ProductRequestDTO productRequestDTO) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        }
        User user = sessionManager.getLoggedUser(ses);
        if (!user.isAdmin()) {
            throw new DeniedPermissionException("You dont have permission for that!");
        }
        return productService.add(productRequestDTO);
    }

    @PutMapping("/products")
    public ProductResponseDTO changeQuantity(HttpSession ses, @RequestBody ProductEditRequestDTO productEditRequestDTO) {
        if (sessionManager.getLoggedUser(ses) == null) {
            throw new AuthenticationException("You have to be logged in!");
        }

        User user = sessionManager.getLoggedUser(ses);
        if (!user.isAdmin()) {
            throw new DeniedPermissionException("You dont have permission for that!");
        }
        return productService.edit(productEditRequestDTO);

    }

    @GetMapping("/products")
    public List<ProductResponseDTO> getAll() {
        return productService.getAll();
    }

    @GetMapping("/products/{id}")
    public ProductResponseDTO getById(@PathVariable(name = "id") int productId) {
        return productService.getById(productId);
    }

    @PostMapping("/products/filter")
    public List<ProductFilterResponseDTO> getAll(@RequestBody ProductFilterRequestDTO productFilterRequestDTO) {
        try {
            return productDAO.getProducts(productFilterRequestDTO);
        } catch (SQLException throwables) {
            //TODO FIX EXCEPTION
            throwables.printStackTrace();
        }
        return null;
    }
}
