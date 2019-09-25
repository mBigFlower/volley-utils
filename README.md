# volley-utils

最近看了hongyang大哥对[okhttp的封装](https://github.com/hongyangAndroid/okhttp-utils)，恰好昨天没事，也整了个Volley的

别问我为什么用Volley，打小就用，顺手了。

## 题外话
volley貌似已经过气了，整 okhttp 吧 ~

## 用法

	compile 'com.flowerfat.volleyutil:volleyutil:0.3.4'

ps1:工具中自带了volley，大家就不要重复添加volley了
ps2:如果你在用demo测试我的工具，不要忘了在manifest中添加网络权限
ps3:如果你用的是Eclipse，直接copy即可，内容很少

## 注意

该工具使用的是单例，方便起见，请在application中添加如下代码以创建请求队列的老大哥

	VolleyUtils.getInstance().init(this);

## 目前支持

- 最基本的四种请求：get, post, delete, put
- 添加header
- 取消请求
- DIY封装Callback
- 自动缓存cookie
- https

## 后续添加

- 【邓超的小儿子】

## 用法示例

### 简单的Get

	VolleyUtils.getInstance()
                .get()
                .url("http://www.baidu.com")
				.tag("MainActivity")
                .addParam("username","bigflower")
                .addHeader("Charset", "UTF-8")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .Go(new StringCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("onSuccess", "正确信息：" + response);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        Log.e("onError", "错误信息：" + errorInfo);
                    }

                });

当然你也可以这样(把链接放到get里更合理点？)：

	VolleyUtils.getInstance()
                .get("http://www.baidu.com")
                .params(new HashMap<>())
                .headers(new HashMap<>())
                .Go(new StringCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("onSuccess", "正确信息：" + response);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        Log.e("onError", "错误信息：" + errorInfo);
                    }

                });

## 改变世界

### 关于这个Util

我会抽空写篇这个工具的简介

### 少做重复的事! 自定义Callback

每个公司的后台不一样，那么网络请求的返回也不同，对于固定的返回，我们可以去自定义回调函数
为了这个，我把Callback弄的累赘的一逼，但是水平有限，只能做到这一步。以后有能力了就改好看点

----------

我们假设服务器的返回值如下：
	
	{
	  "success": true，
	  "data": {
	    "id": 7527，
	    "username": "bigflower"
	  }，
      "errorCode":0，
      "errorInfo":null
	}

再假设我们使用的是StringRequest，那么我们每次都要这么去解析我们的代码

	...
	.Go(new Callback() {
            @Override
            public void onSuccess(Object response) {
				doHttpGetCallback(response);
            }

            @Override
            public void onError(String errorInfo) {
				...
            }
        });

	private void doHttpGetCallback(String response) {
        try {
            JSONObject json = new JSONObject(response);
            if (json.getBoolean("success")) {
                String data = json.getString("data");
                Log.i("doHttpGetCallback", data);
            } else {
                int errorCode = json.getInt("errorCode");
                if (errorCode == 1) {
                    Log.e("doHttpGetCallback", "eg:手机号格式不对");
                } else if (errorCode == 2) {
                    Log.e("doHttpGetCallback", "eg:系统出错");
                }
            }
        } catch (Exception e) {
            Log.e("doHttpGetCallback", "数据解释失败：" + e.getMessage());
        }
    }

每次都要去将判断success写在Activity中

----------

我们不妨把冗余的动作放到工具类BigCallback中， 如下：

	public abstract class BigCallback extends Callback<String> {
	    @Override
	    public Decide dataBeautifulPlus(String response) throws IOException {
	        return doResponse(response);
	    }
	
	    private Decide doResponse(String response) {
	        return new Decide(true, "\nI am user design\n\n" + response);
	    }
	}

这里我们引入了一个自定义entity----Decide， 两个参数，第一个是boolean类型，比如返回“手机号格式不对”，则应该执行error回调，这个参数设定为false。如果返回登录成功，则应该执行seccess回调，那么就设定为true；第二个是回传的数据或者是错误的提示。



## 自动缓存cookie

原谅我菜， 我看hongyang大哥的session保持，洋洋洒洒用了两个大类， 什么CookieStore啊，什么HttpCookie啊。
我表示整不懂。。。

### 具体实现

相关类有两个 VolleyUtils 和 VolleyRequest

----------
[VolleyUtils](https://github.com/mBigFlower/volley-utils/blob/master/app/src/main/java/com/flowerfat/volleyutils/utils/VolleyUtils.java)

若要使用自动保持cookie， 则在VolleyUtils初始化的时候，在application中使用

	VolleyUtils.getInstance().init(this, true); // 比之前多加了个参数 true

具体我们是用SharedPreferences保存的String类型，详见函数 setCookie 与 getCookie

----------
[VolleyRequest](https://github.com/mBigFlower/volley-utils/blob/master/app/src/main/java/com/flowerfat/volleyutils/utils/VolleyRequest.java)

在请求前从SharedPreferences中获得cookie并放到请求头里， 详见 saveCookie 和 setCookie 。 有自定义需求该这里就好

## Https

吃井不忘挖水人，再次感谢hongyang大哥的帮助，看了他的《当okHttp遇到https》，再结合okHttp-Utils这个库中的httpsUtils类，成功实现了Volley的https请求，包括单向和双向验证。

[我的这篇文章更加详细说明了这部分](http://blog.csdn.net/liudehuaii18/article/details/50358841)

[如果服务端证书的生成是用OpenSSL，你可以参考这篇文章](http://blog.csdn.net/liudehuaii18/article/details/50373652)

### 添加单向验证证书

依旧实在初始化的时候添加证书，即在Application中

    try {
        VolleyUtils.getInstance().init(this, getAssets().open("boy_server.cer"));
    } catch (Exception e){

    }

把我们的证书放到assets中，然后在init中加进去就好

### 添加双向验证证书

	try {
        VolleyUtils.getInstance().init(this, getAssets().open("girl_client.bks"),
                "123456",getAssets().open("boy_server.cer"));
    } catch (Exception e){

    }

这里除了之前的单向的，还要加上另外的一对证书中的一个，以及密码（两个证书注意顺序哈）。证书我们用portecle处理成了bks格式。嗯android就是要bks

## Log
默认为debug模式，会打印请求信息，如果不想看log，请：

	VolleyUtils.getInstance().debug(false);

