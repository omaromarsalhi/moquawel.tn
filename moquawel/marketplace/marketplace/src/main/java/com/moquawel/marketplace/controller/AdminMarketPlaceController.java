package com.moquawel.marketplace.controller;


import com.moquawel.marketplace.request.ServiceRequest;
import com.moquawel.marketplace.service.CategoryService;
import com.moquawel.marketplace.service.MyServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/marketplace/admin")
@RequiredArgsConstructor
public class AdminMarketPlaceController {

    private final MyServiceService myServiceService;
    private final CategoryService categoryService;


    @DeleteMapping("/category/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable String categoryId) {
        var response = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(response.message(), response.status());
    }

    @PostMapping("/category/save")
    public ResponseEntity<Void> saveCategory(@RequestParam String request) {
        categoryService.saveCategory(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/service/save")
    public ResponseEntity<Void> saveService(@RequestBody ServiceRequest request) {
        myServiceService.saveService(request);
        return ResponseEntity.ok().build();
    }


}
