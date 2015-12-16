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
- 【邓超的小儿子】

## 用法示例

### 简单的Get

	VolleyUtils.getInstance()
                .get()
                .url("http://www.baidu.com")
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

