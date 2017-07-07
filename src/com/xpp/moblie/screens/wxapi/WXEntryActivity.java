package com.xpp.moblie.screens.wxapi;

import java.util.logging.LogManager;












import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.screens.HomeActivity;
import com.xpp.moblie.screens.OrderTotalActivity;
import com.xpp.moblie.util.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/** ΢�ſͻ��˻ص�activityʾ�� */  
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {  
    // IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�  
    private IWXAPI api;  
    private	SharedPreferences settings ;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID,true); 
        api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(), this);  
        super.onCreate(savedInstanceState);  
    }  
    @Override  
    public void onReq(BaseReq arg0) {
    	System.out.println("΢�ŷ��� ----- "+arg0.openId);
    }  
  
    @Override  
    public void onResp(BaseResp resp) {  
//        LogManager.show(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"  
//                + resp.errStr, 1);  
        switch (resp.errCode) {  
        case BaseResp.ErrCode.ERR_OK:  
        	System.out.println("����ɹ�  ");
//        	settings = getSharedPreferences(DataProviderFactory.PREFS_NAME,
//    				Context.MODE_PRIVATE);
//        	String custId=settings.getString(DataProviderFactory.PREFS_CUSTID, null);
//        	String wxreturn=settings.getString(DataProviderFactory.PREFS_WXRETURN, null);
//        	Intent intent;
//        	System.out.println("custId:"+custId);
//        	System.out.println("wxreturn:"+wxreturn);
//        	Customer customer=Customer.findByCustId(custId);
        	
//        	if ("OrderTotalActivity".equals(wxreturn)&&customer!=null) {
//        	 intent = new Intent(this, OrderTotalActivity.class);
//        	 intent.putExtra("custInfo", customer);
//			}
//        	else {
//        	 intent = new Intent(this, HomeActivity.class);
//			}
        
//        	startActivity(intent);
//        	finish();
            //����ɹ�  
            break;  
        case BaseResp.ErrCode.ERR_USER_CANCEL:  
        	System.out.println("����ȡ��  ");
            //����ȡ��  
            break;  
        case BaseResp.ErrCode.ERR_AUTH_DENIED:  
        	System.out.println("����ܾ� ");
            //����ܾ�  
        	
            break;  
        }  
        XPPApplication.exit(WXEntryActivity.this);
    }  
}  
