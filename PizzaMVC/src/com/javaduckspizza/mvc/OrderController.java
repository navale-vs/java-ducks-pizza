package com.javaduckspizza.mvc;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.javaduckspizza.dao.TypesDao;
import com.javaduckspizza.dao.interfaces.ITypesDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.TypesVo;

@Controller
public class OrderController {
	@RequestMapping(value="/types", method=RequestMethod.GET)
	public ModelAndView GetTypesByCategory(String category) {
		ITypesDao typesDao = new TypesDao();
		Session session = SessionUtil.getInstance().openSession();
		List<TypesVo> lstTypesVo = typesDao.getByCategory(category, session);
		session.close();

		return new ModelAndView("types", "command", lstTypesVo);
	}

	
}
