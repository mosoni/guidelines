/**
 * 
 */

package com.pidilite.order.web.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * The purpose of this class is to handle operations for a particular Order i.e.
 * Create, Update and Delete Accessibility : This class gets executed from Order
 * Module available to end users. When end user performs Create/Update/Delete
 * Order, flow comes to this class on respective method.
 *
 * @author Mohit Soni
 */
public class OrderAction extends MVCPortlet {

	/**
	 * This method is used to Create the order. When end user create on place
	 * order this method gets executed which further calls Create order API to
	 * create the order.
	 *
	 * @param actionRequest
	 *            : Holds the parameters submitted by End user like Product
	 *            name, quantity etc
	 * @param actionResponse
	 *            : Holds the response with success/failure/validation to end
	 *            user
	 * @throws IOException
	 * @throws PortletException
	 *             : Hold detail of exception if there is problem while creating
	 *             order
	 */
	public void createOrder(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String productName = ParamUtil.getString(actionRequest, "productName");
		long productId = ParamUtil.getLong(actionRequest, "productId");
		long productQuantity =
			ParamUtil.getLong(actionRequest, "productQuantity");

		ThemeDisplay themeDisplay = getThemeDisplay(actionRequest);
		/* API called to update the order */
		Order order = _orderLocalService.deleteOrder(
			productName, productId, productQuantity, themeDisplay);

	}

	/**
	 * This method is used to Update the order. When end user updates an order
	 * this method gets executed which further calls Update order API to update
	 * the order.
	 *
	 * @param actionRequest
	 *            : Holds the parameters submitted by End user like OrderId ,
	 *            Product name, quantity etc
	 * @param actionResponse
	 *            : Holds the response with success/failure/validation to end
	 *            user
	 * @throws IOException
	 * @throws PortletException
	 *             : Hold detail of exception if there is problem while updating
	 *             order
	 */
	public void updateOrder(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String productName = ParamUtil.getString(actionRequest, "productName");
		long orderId = ParamUtil.getLong(actionRequest, "orderId");
		long productId = ParamUtil.getLong(actionRequest, "productId");
		long productQuantity =
			ParamUtil.getLong(actionRequest, "productQuantity");

		ThemeDisplay themeDisplay = getThemeDisplay(actionRequest);

		/* API called to update the order */
		Order order = _orderLocalService.deleteOrder(
			orderId, productName, productId, productQuantity, themeDisplay);
	}

	/**
	 * This method is used to delete the order. When end user deletes an order
	 * this method gets executed which further calls Delete order API to update
	 * the order.
	 *
	 * @param actionRequest
	 *            : Holds the parameters submitted by End user like OrderId ,
	 *            reason etc
	 * @param actionResponse
	 *            : Holds the response with success/failure/validation to end
	 *            user
	 * @throws IOException
	 * @throws PortletException
	 *             : Hold detail of exception if there is problem while updating
	 *             order
	 */
	public void deleteOrder(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		long orderId = ParamUtil.getLong(actionRequest, "orderId");
		String reason = ParamUtil.getString(actionRequest, "reason");

		ThemeDisplay themeDisplay = getThemeDisplay(actionRequest);

		/* API called to delete the order */

		try {
			Order order =
				_orderLocalService.deleteOrder(orderId, reason, themeDisplay);
		}
		catch (PortletException e) {
			_log.error(e);
		}

	}

	/*
	 * This method is used to get the ThemeDisplay which will further used to
	 * get user details
	 * @param actionRequest :Holds ThemeDisplay
	 * @return : ThemeDisplay
	 */
	private ThemeDisplay getThemeDisplay(ActionRequest actionRequest) {

		return (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	@Reference(unbind = "-")
	protected void setOrderLocalServiceService(
		OrderLocalService orderLocalService) {

		_orderLocalService = orderLocalService;
	}

	private OrderLocalService _orderLocalService;

}
