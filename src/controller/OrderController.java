package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mysql.cj.xdevapi.Result;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import entity.ServerResponse;
import service.OrderService;

@Controller
@RequestMapping(value = "/order/")
public class OrderController {

	@Autowired
	OrderService os;

	@ResponseBody
	@RequestMapping(value = "addMedToCurOrder.do", method = RequestMethod.POST)
	public ServerResponse addMed(HttpServletRequest rs) {
		int user_id = Integer.parseInt(rs.getParameter("user_id"));
		int med_id = Integer.parseInt(rs.getParameter("med_id"));
		int result = os.addMedToCurOrder(med_id, user_id);
		if (result == 1) {
			return ServerResponse.createBySuccessMessage("�ɹ����빺�ﳵ");
		} else {
			return ServerResponse.createByErrorMessage("��ӹ��ﳵʧ��");
		}
	}

	@ResponseBody
	@RequestMapping(value = "getMedListFromCurOrder.do", method = RequestMethod.GET)
	public ServerResponse getCurMedList(HttpServletRequest rs) {
		int user_id = Integer.parseInt(rs.getParameter("user_id"));
		return ServerResponse.createBySuccess("��ȡ���ﳵҩƷ�ɹ�", os.getMedListFromCurOrder(user_id));
	}

	@ResponseBody
	@RequestMapping(value = "subMednum.do", method = RequestMethod.POST)
	public ServerResponse subMednum(HttpServletRequest rs) {
		int om_id = Integer.parseInt(rs.getParameter("om_id"));
		int user_id = Integer.parseInt(rs.getParameter("user_id"));
		int result = os.subMednumInOrder(om_id, user_id);
		if (result == 1) {
			return ServerResponse.createBySuccessMessage("�ɹ�����ҩƷ����");
		} else if (result == 0) {
			return ServerResponse.createByErrorMessage("����ҩƷ����ʧ��");
		} else {
			return ServerResponse.createBySuccessMessage("�ɹ�����ҩƷ���������ܶ�ʧ��");
		}
	}

	@ResponseBody
	@RequestMapping(value = "postNormalOrder.do", method = RequestMethod.POST)
	public ServerResponse postNormalOrder(HttpServletRequest rs) {
		int user_id = Integer.parseInt(rs.getParameter("user_id"));
		int speed = Integer.parseInt(rs.getParameter("speed"));
		String phone = rs.getParameter("user_phone");
		String name = rs.getParameter("user_name");
		String address = rs.getParameter("user_address");
		int result = os.payNormalOrder(user_id, speed,name,phone,address);
		switch (result) {
		case 1:
			return ServerResponse.createBySuccessMessage("����֧���ɹ�");
		case -1:
			return ServerResponse.createByErrorMessage("Ǯ������!");
		case -2:
			return ServerResponse.createByErrorMessage("�û����۳�ʧ�ܣ�����֧��ʧ��!");
		case 0:
			return ServerResponse.createByErrorMessage("���۳��ɹ��������ύʧ��");
		case 3:
			return ServerResponse.createBySuccessMessage("����֧���ɹ������ݿ�����ܶ�ʧ��");
		default:
			return ServerResponse.createByErrorMessage("δ֪����");
		}

	}
	
	
	@ResponseBody
	@RequestMapping(value="postPrescriptOrder.do", method=RequestMethod.POST)
	public ServerResponse postPrescriptOrder(MultipartHttpServletRequest mrs) {
		int user_id = Integer.parseInt(mrs.getParameter("user_id"));
		String user_name = mrs.getParameter("user_name");
		String user_phone = mrs.getParameter("user_phone");
		String user_address = mrs.getParameter("user_address");
		int speed = Integer.parseInt(mrs.getParameter("speed"));
		MultipartFile file = mrs.getFile("pic");
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return ServerResponse.createByErrorMessage("ͼƬ�ϴ�ʧ��");
		} //��ȡ�ļ�������
		OutputStream osm=null;
		try {
			String path = "D:\\FTPStoreRoot\\prescipt_order_verify_pic\\";
			byte[] bs = new byte[1024];
			int len;
			File tmpFile = new File(path);
			if (!tmpFile.exists()) {
				tmpFile.mkdirs();
			}
			osm = new FileOutputStream(tmpFile.getPath()+File.separator+filename);
			while((len = inputStream.read(bs))!= -1) {
				osm.write(bs,0,len);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				osm.close();
				inputStream.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		//ͼƬ��ŷ�������ַ
		String picPath = "http://245z3l3522.qicp.vip/file/prescipt_order_verify_pic/"+filename;
		int result = os.postPrescriptOrder(user_id, speed, user_name, user_phone, user_address, picPath);
		switch (result) {
		case 1:
			return ServerResponse.createBySuccessMessage("�������ύ���");
		case -1:
			return ServerResponse.createByErrorMessage("Ǯ������!");
		case -2:
			return ServerResponse.createByErrorMessage("�û����۳�ʧ�ܣ�����֧��ʧ��!");
		case 0:
			return ServerResponse.createByErrorMessage("���۳��ɹ��������ύʧ��");
		case 3:
			return ServerResponse.createBySuccessMessage("����֧���ɹ������ݿ�����ܶ�ʧ��");
		default:
			return ServerResponse.createByErrorMessage("δ֪����");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="getWaitingOrderList.do",method=RequestMethod.GET)
	public ServerResponse getWaitingOrder() {
		return ServerResponse.createBySuccess("��ȡ�����б�ɹ�", os.getWaitingOrderByTimeDesc());
	}
	
	@ResponseBody
	@RequestMapping(value="getOrderDetail.do",method=RequestMethod.GET)
	public ServerResponse getOrderDetail(HttpServletRequest rs) {
		int order_id = Integer.parseInt(rs.getParameter("order_id"));
		return ServerResponse.createBySuccess("��ȡ��������ɹ�", os.getOrderDetailById(order_id));
	}
	
	@ResponseBody
	@RequestMapping(value="takeOrder.do",method=RequestMethod.POST)
	public ServerResponse takeOrder(HttpServletRequest rs) {
		int dist_id = Integer.parseInt(rs.getParameter("dist_id"));
		int order_id = Integer.parseInt(rs.getParameter("order_id"));
		int result = os.takeOrderByDistId(dist_id, order_id);
		if (result == 0) {
			return ServerResponse.createByErrorMessage("��ȡ����ʧ��");
		}else {
			return ServerResponse.createBySuccessMessage("�ɹ���ȡ����");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="finishOrder.do",method=RequestMethod.GET)
	public ServerResponse finishOrder(HttpServletRequest rs) {
		int order_id = Integer.parseInt(rs.getParameter("order_id"));
		int result = os.finishOrder(order_id);
		if (result == 0) {
			return ServerResponse.createByErrorMessage("��ɶ���ʧ��");
		}else {
			return ServerResponse.createBySuccessMessage("�ɹ���ɶ���");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="getTakedOrderList.do",method=RequestMethod.GET)
	public ServerResponse getTakedOrder(HttpServletRequest rs) {
		int dist_id = Integer.parseInt(rs.getParameter("dist_id"));
		return ServerResponse.createBySuccess("��ȡ�ѽӵ��б�ɹ�", os.getTakedOrder(dist_id));
	}
	
	@ResponseBody
	@RequestMapping(value="getFinishedOrderList.do",method=RequestMethod.GET)
	public ServerResponse getFinishedOrder(HttpServletRequest rs) {
		int dist_id = Integer.parseInt(rs.getParameter("dist_id"));
		return ServerResponse.createBySuccess("��ȡ������б�ɹ�", os.getFinishedOrder(dist_id));
	}


}
