package com.kh.admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kh.admin.model.service.ItemService;
import com.kh.admin.model.vo.Item;
import com.kh.admin.model.vo.ItemCategory;
import com.kh.common.model.vo.ItemListResponse;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class ItemListController
 */
@WebServlet("/list.it")
public class ItemListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category");
		
		int listCount; // 총 게시글 개수 - 전체 select문에서 사용할 것 
		int categoryListCount; // - 일부 select문에서 사용할 것
		int currentPage =1; // 현재 요청한 페이지
		int pageLimit; //페이지 하단에 보여질 페이징바의 페이지 최대 개수(나는 5)
		int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수(나는 5)
		int maxPage; // 총 페이지 수 - 전체 select문
		int categorymaxPage; //총 페이지 수 - 일부 select문
		int startPage; // 페이징바의 시작수
		int endPage; //페이징바의 끝수
		
		// 현재 페이지 -> 쿼리스트링으로 넘김
		currentPage = Integer.parseInt(request.getParameter("cpage"));
		// 페이지 최대 개수 (나는 5개 할거임)
		pageLimit = 5;
		// 한 페이지에 보여질 게시글 수 (나는 5개 할거임)
		boardLimit = 5;
		// 페이징바 시작 수
		startPage = (currentPage-1) / pageLimit * pageLimit +1;
		// 페이징바 마지막 수
		endPage = startPage + pageLimit-1;
		
		if(category.equals("IC100")) { 
			// 선택 안된 경우
			// 총 게시글 수 : listCount
			listCount = new ItemService().selectListCount();
			// 총 페이지 수
			maxPage = (int)Math.ceil((double)listCount/boardLimit);
			// endPage = maxPage로 변경
			if(endPage>maxPage) {
				endPage = maxPage;
			}// 내부 if문
			
			// 1. pageInfo 가공(조회, 페이징바 선택시)
			PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
			// 2. 서비스에게 정보 요청(전체)		
			ArrayList<Item> list = new ItemService().selectItemList(pi);
	
			// 3. 응답 던지기
			request.setAttribute("defaultCategory", category);
			request.setAttribute("pi", pi);
			request.setAttribute("list", list); // 아이템 리스트 객체
			request.getRequestDispatcher("/views/shop/minglesShops.jsp").forward(request, response);
		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
