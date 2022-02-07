package com.qinzi123.controller.micro;

import com.qinzi123.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProductController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/22 0022 11:27
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "产品信息")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "添加产品信息", notes = "添加产品信息")
    @RequestMapping(value = "/product/addProduct", method = RequestMethod.POST)
    private int addProduct(@RequestBody Map map) {
        return productService.addProduct(map);
    }

    @ApiOperation(value = "修改产品信息", notes = "修改产品信息")
    @RequestMapping(value = "/product/updateProduct", method = RequestMethod.POST)
    private int updateProduct(@RequestBody Map map) {
        return productService.updateProduct(map);
    }

    @ApiOperation(value = "删除产品", notes = "删除产品")
    @RequestMapping(value = "/product/deleteProductById", method = RequestMethod.POST)
    private int deleteProductById(@RequestBody Map map) {
        return productService.deleteProductById(map);
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listProduct(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return productService.listProduct(card, start, num);
    }

    @RequestMapping(value = "/product/getOtherImagesById", method = RequestMethod.POST)
    private List<LinkedHashMap> getOtherImagesById(@RequestBody Map map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        return productService.getOtherImagesById(productId);
    }

    @RequestMapping(value = "/product/getReleaseCompanyInfoByCardId", method = RequestMethod.POST)
    private Map<String,Object> getReleaseCompanyInfoByCardId(@RequestBody Map map) {
        return productService.getReleaseCompanyInfoByCardId(map);
    }

    @RequestMapping(value = "/product/getReleaseCompanyInfoByCompanyId", method = RequestMethod.POST)
    private List<LinkedHashMap> getReleaseCompanyInfoByCompanyId(@RequestBody Map map) {
        return productService.getReleaseCompanyInfoByCompanyId(map);
    }
}
