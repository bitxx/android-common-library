# android-common-library
<html>
<body>
<a name="914"/>
<div><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">主要</span><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">将一些感兴趣的基础内容或者自定义组件添加进来，后期会根据需要，拆分该项目中的组件到独立项目中</span> ，<span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">会尽量避免使用第三方包。记录些重要内容，其它的可以忽略。</span></div>
<img src="https://github.com/jason-wj/android-common-library/blob/master/image/demo.png" type="image/png" style="height:auto;" width="255"/>

##2016年06月26日更新
###新增:EmptyLayout以及ClearEditText：
<span>
    <div><br/></div>
    <div>
        <span style="font-size: 16px;">
            <span style="font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;;">
                <span style="background-color: rgb(255, 255, 255);">
                    <span style="color: rgb(51, 51, 51);"></span>
                </span>
            </span>
        </span>
    </div>
    <ol>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                    新增：EmptyLayout，可自行修改文字描述和图片，以及单击重试的监听实现。<br/>
                </span>
            </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：可清空的EditText，可通过替换右侧的drawable来更换清空图标<br/>
            </span>
        </li>
     </ol>
</span>


##2016年06月25日更新
###新增:图片缩放处理(GestureImageView)：
<span>
    <div><br/></div>
    <div>
        <span style="font-size: 16px;">
            <span style="font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;;">
                <span style="background-color: rgb(255, 255, 255);">
                    <span style="color: rgb(51, 51, 51);"></span>
                </span>
            </span>
        </span>
    </div>
    <ol>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                    注意：该GestureImageView继承自ImageView,但由于使用了自定义地Matrix,在外部直接使用ImageView自己地setScaleType无效,需要使用GestureImageView的setType方法来实现<br/>
                </span>
            </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：手势缩放图片<br/>
            </span>
        </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：多触控点移动图片<br/>
            </span>
        </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：单击缩放图片<br/>
            </span>
        </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：可设置多种图片初始显示类型,目前实现:Type.FIT_XY、Type.FIT_START、Type.FIT_CENTER、Type.CENTER<br/>
            </span>
        </li>
     </ol>
</span>


##2016年06月12日更新
###新增：GIF动画播放：
<span>
    <div><br/></div>
    <div>
        <span style="font-size: 16px;">
            <span style="font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;;">
                <span style="background-color: rgb(255, 255, 255);">
                    <span style="color: rgb(51, 51, 51);"></span>
                </span>
            </span>
        </span>
    </div>
    <ol>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：设置播放次数<br/>
            </span>
        </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：设置是否暂停<br/>
            </span>
        </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：设置恢复初始动画状态<br/>
            </span>
        </li>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                新增：监听指定次数播放完毕后的操作（播放次数times设置大于0时有效）<br/>
            </span>
        </li>
     </ol>
</span>


##2016年06月07日更新
<span>
    <div><br/></div>
    <div>
        <span style="font-size: 16px;">
            <span style="font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;;">
                <span style="background-color: rgb(255, 255, 255);">
                    <span style="color: rgb(51, 51, 51);"></span>
                </span>
            </span>
        </span>
    </div>
    <ol>
        <li>
            <span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">
                GIF动画实现，继承自ImageView，目前只实现了基本的功能，后续将进一步完善<br/>
            </span>
        </li>
     </ol>
</span>

##2016年06月06日更新
<div>
<span><div><br/></div><div><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">主要</span><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">将一些感兴趣的基础内容或者自定义组件添加进来</span> ，<span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">会尽量避免使用第三方包。记录些重要内容，其它的可以忽略。</span></div><div><span style="font-size: 16px;"><span style="font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;;"><span style="background-color: rgb(255, 255, 255);"><span style="color: rgb(51, 51, 51);"></span></span></span></span></div><ol><li><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">json原生生成与解析的实现<br/></span></li><li><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">自定义ScrollView上拉下滑</span></li><li><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">加入了部分android5.0新的元素</span></li><li><span style="font-size: 16px;"><span style="font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;;"><span style="background-color: rgb(255, 255, 255);"><span style="color: rgb(51, 51, 51);">头像切换封装（相册选择、照相获取，同时进行剪切）</span></span></span></span></li><li>加入了些多项目通用的工具类，可在library_wj中查找</li><li><span style="color: rgb(51, 51, 51); font-family: &apos;Helvetica Neue&apos;, Helvetica, &apos;Segoe UI&apos;, Arial, freesans, sans-serif, &apos;Apple Color Emoji&apos;, &apos;Segoe UI Emoji&apos;, &apos;Segoe UI Symbol&apos;; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; display: inline !important; float: none; background-color: rgb(255, 255, 255);">现阶段只是初步完成了项目结构等等，后面将会不定期更新其中的内容，作为学习或者是对当前工作的总结<br/></span></li></ol></span>
</div>
</body>
</html>
