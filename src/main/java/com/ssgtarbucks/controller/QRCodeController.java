package com.ssgtarbucks.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.ssgtarbucks.domain.QRCodeDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.StorageDTO;
import com.ssgtarbucks.domain.TokenDTO;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.jwt.JwtUtil;
import com.ssgtarbucks.service.QRCodeService;
import com.ssgtarbucks.service.UserService;



@Controller
@RequestMapping("/api/v1/qrcode")
public class QRCodeController {

	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	JwtUtil jwtUtil;

	@Value("${jwt.name}")
	String tokenKey;

	@Value("${qrcode.link.url}")
	String qrcode_link;

	@Value("${qrcode.path.url}")
	String qrcode_path;


	@Autowired
	private QRCodeService qrCodeService;

	@GetMapping("/search/{qrcode_id}")
	@ResponseBody
	public ResponseEntity<StorageDTO> search(@PathVariable("qrcode_id") int qrcode_id) {

		System.out.println("QRCodeController - /search(GET) >>>");

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");

		StorageDTO responseData = qrCodeService.joinStroagebyQRCodeIdToSearch(qrcode_id);

		return new ResponseEntity<>(responseData, header, HttpStatus.OK);
	}
	
	//QR코드등록 -> 보관장소등록 (forward)
	@PostMapping("/branch/location/new")
	public ModelAndView registerLocation(@RequestBody(required = false) List<StockLocationDTO> list,
			@RequestParam(required = false) String branch_id, HttpSession rttr) {

		System.out.println("QRCodeController - /branch/location/new (Post) >>>" + list + "  " + branch_id);
		rttr.setAttribute("list", list);
		rttr.setAttribute("branch_id", branch_id);

		return new ModelAndView("forward:/api/v1/branch/location/new");
	}
	
	
	
	
	
	//QR코드등록 -> 보관장소등록 (forward)
	@PostMapping("/branch/location/new2")
	public ModelAndView registerLocation2(@RequestBody(required = false) List<StockLocationDTO> list,
			@RequestParam(required = false) String branch_id,RedirectAttributes rttr) {

		System.out.println("QRCodeController - /branch/location/new (Post) >>>" + list + "  " + branch_id);
		rttr.addFlashAttribute("list",list);
		rttr.addFlashAttribute("branch_id", branch_id);

		return new ModelAndView("forward:/api/v1/branch/location/new");
	}
	

}