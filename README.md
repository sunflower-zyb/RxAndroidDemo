# RxAndroidDemo
RxJava+Retrofit网络请求框架Demo

* 分割网络请求结果[Response](https://github.com/sunflower-zyb/RxAndroidDemo/blob/master/app%2Fsrc%2Fmain%2Fjava%2Fcom%2Fsunflower%2Frxandroiddemo%2Fdto%2FResponse.java)

      如果接口调用成功，且返回结果正常(如登录成功)，返回泛型T；如果返回结果异常（如密码错误等），则返回自定义异常`APIException(String code, String message)`

* log请求地址+参数
  ![log请求参数+地址](http://7xlgmj.com1.z0.glb.clouddn.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20160218175850.png)
* 测试多种网络请求参数情况，如上传数组、单个文件、多个文件等
