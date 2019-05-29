package com.model2.mvc.web.product;


import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
	
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@Value("#{commonProperties['fileroot']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	String fileroot;
	
	
	//@RequestMapping("/addProductView.do")
	//public String addProductView() throws Exception {
	@RequestMapping( value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception{
		System.out.println("/addProduct : GET");
		
		return "redirect:/product/addProductView.jsp"; 
	}
	
//@RequestMapping("/addProduct.do")
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct( @ModelAttribute("vo") Product product,@RequestParam("uploadFile") MultipartFile uploadFile ) throws Exception {

		System.out.println("/addProduct : POST");
		
		String fileName = uploadFile.getOriginalFilename();
		System.out.println("���ϳ�����"+fileName);
		File f = new File(fileroot, fileName);
		
		try {
			uploadFile.transferTo(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("/addProduct : POST");
		//Business Logic
		String manuDate = product.getManuDate().replaceAll("-","");
		product.setManuDate(manuDate);
		product.setFileName(fileName);
		
		productService.addProduct(product);;
		
		return "forward:/product/addProduct.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo , 
			Model model,
			HttpServletRequest request) throws Exception {
		
		int ProdNo = Integer.parseInt(request.getParameter("prodNo"));
		Product product = productService.getProduct(prodNo);
		
		System.out.println("�� ����ü ������?"+product);
		
		System.out.println("/getProduct.do");
		//Business Logic
		
		// Model �� View ����
		model.addAttribute("vo", product);
		
		System.out.println("�� ����ü ������?"+product);
		
		String menu = request.getParameter("menu");
		if (menu != null) {
			if (menu.equals("manage")) {
				return "forward:/product/updateProductView.jsp";
			}else{
				return "forward:/product/getProduct.jsp";
			}
		}else{
			return "forward:/product/getProduct.jsp";
		}
		
		
	}
	
	//@RequestMapping("/updateProductView.do")
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		
		System.out.println("/������Ʈ���δ�Ʈ : GET");
		
		System.out.println("���ε�ѹ�����??"+prodNo);
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("vo", product);
		
		
		
		
		return "forward:/product/updateProductView.jsp";
	}
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct( @ModelAttribute("vo") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("������Ʈ����Ʈ���.do");
		
		System.out.println("���ε�ѹ� �Ѿ�Գ�"+product);
		
		//Business Logic
		productService.updateProduct(product);
		
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}


	//@RequestMapping("/listProduct.do")
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct : GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping(value="listProduct2")
	public String listProduct2( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct : GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct2.jsp";
	}
	/*@RequestMapping(value = "listProduct")
	public ModelAndView listProduct( @ModelAttribute("search") Search search , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("forward:/product/listProduct.jsp");
		
		return modelAndView;
	}*/
}