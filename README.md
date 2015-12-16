# volley-utils

最近看了hongyang大哥对[okhttp的封装](https://github.com/hongyangAndroid/okhttp-utils)，恰好昨天没事，也整了个Volley的

别问我为什么用Volley，打小就用，顺手了。

## 用法

1. 添加Volley库  compile 'com.mcxiaoke.volley:library:1.0.19'
2. 把utils和io复制到工程里（以后我会放到maven中）
3. 如果你在用demo测试我的工具，不要忘了在manifest中添加网络权限

## 注意

该工具使用的是单例，方便起见，请在application中添加如下代码以创建请求队列的老大哥

	VolleyUtils.getInstance().init(this);

## 目前支持

- 最基本的四种请求：get, post, delete, put
- 添加header
- 取消请求

## 后续添加

- 自定义封装Callback
- 自动缓存cookie
- https (找不到资料，有谁会啊)
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
                .Go(new Callback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        
                    }

                    @Override
                    public void onError(String errorInfo) {

                    }
                });

当然你也可以这样(把链接放到get里更合理点？)：

	VolleyUtils.getInstance()
                .get("http://www.baidu.com")
                .params(new HashMap<>())
                .headers(new HashMap<>())
                .Go(new Callback() {
                    @Override
                    public void onSuccess(Object response) {

                    }

                    @Override
                    public void onError(String errorInfo) {

                    }
                });

## 改变世界

### 关于这个Util

我会抽空写篇这个工具的简介

### 少做重复的事

每个公司的后台不一样，那么网络请求的返回也不同，对于固定的返回，我们可以去fuck这个类：VolleyRequest

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

我们不妨把冗余的动作放到工具类VolleyRequest中， 如下：

	private void doVolleyResponse(String volleyResponse, Callback<String> listener){
        try {
            JSONObject response = new JSONObject(volleyResponse);
            int responseCode = response.getInt("errorCode");
            switch (responseCode) {
                case 0:
                    listener.onSuccess(response.getString("data"));
                    break;
                case 1:
                    listener.onError("1.手机号格式不对");
                    break;
                case 2:
                    listener.onError("2.系统出错");
                    break;
            }
        } catch (Exception e) {
            listener.onError("catch : "+e.getMessage());
        }
    }

这样我们直接在MainActivity中，这样：

	...
	.Go(new Callback<String>() {
            @Override
            public void onSuccess(String data) {
				// 这里data就是我们要处理的数据
				//eg:
				User user = new Gson().fromJson(data, User.class);
            }

            @Override
            public void onError(String errorInfo) {
				// 这里我们可以直接输出错误信息
                Toast.makeText(MainActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
            }
        });