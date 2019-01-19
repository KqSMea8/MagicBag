package org.github.helixcs.argtree.sampleargs;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Accessors(chain = true)
@Data
public class PsbCheckInUploadStandardRQ implements Serializable {
    // ==> 通用参数

    /**
     * traceId
     */
    private String traceId;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * channelId 同 psbType，水滴路由
     */
    private String channelId;

    /**
     * 数据来源 , 来源系统代码
     */
    private String source;
    /**
     * 拓展字段
     */
    private JSONObject extData;

    // ==> 业务参数
    // psbType 从hfman 获取, 同 channelId
    private String psbType;

    // psbName 在hfman 配置
    private String psbName;

    // 旅馆代码 在hfman 配置
    private String innCode;

    // psb 配置信息 , json格式
    // psbUrl ,  psbPassword , psbAuth .....
    private String psbConfig;

    // 用户信息列表
    private List<GuestInfo> guestInfoList;

    public PsbCheckInUploadStandardRQ setExtData(JSONObject extData) {
        this.extData = extData;
        return this;
    }

    // 设置String类型拓展字段
    public PsbCheckInUploadStandardRQ setExtData(String extStringData) {
        if (null == this.getExtData()) {
            JSONObject newJson = JSONObject.parseObject(extStringData);
            this.setExtData(newJson);
            return this;
        }
        this.setPsbConfig(JSONObject.parseObject(extStringData));
        return this;
    }

    // 新增String类型拓展字段
    public PsbCheckInUploadStandardRQ addExtData(String extStringData) {
        if (null == this.getExtData()) {
            JSONObject newJson = JSONObject.parseObject(extStringData);
            this.setExtData(newJson);
            return this;
        }
        this.getExtData().putAll(JSONObject.parseObject(extStringData));
        return this;
    }

    // 获取String类型拓展字段
    public Optional<String> getStringExtData() {
        return Optional.ofNullable(this.getExtData() == null ? null : this.getExtData().toJSONString());
    }

    public PsbCheckInUploadStandardRQ setPsbConfig(String psbConfig) {
        this.psbConfig = psbConfig;
        return this;
    }

    // 新增 JSONObject 类型 psbConfig
    public PsbCheckInUploadStandardRQ addPsbConfig(JSONObject jsonPsbConfig) {
        if (null == this.getPsbConfig() || this.getPsbConfig().length() < 1) {
            this.setPsbConfig(jsonPsbConfig.toJSONString());
            return this;
        }
        jsonPsbConfig.putAll(JSONObject.parseObject(this.getPsbConfig()));
        this.setPsbConfig(jsonPsbConfig.toJSONString());
        return this;
    }

    // 设置 JSONObject 类型 psbConfig
    public PsbCheckInUploadStandardRQ setPsbConfig(JSONObject jsonPsbConfig) {
        if (null == this.getPsbConfig() || this.getPsbConfig().length() < 1) {
            this.setPsbConfig(jsonPsbConfig.toJSONString());
            return this;
        }
        this.setPsbConfig(jsonPsbConfig.toJSONString());
        return this;
    }

    public Optional<JSONObject> getJsonPsbConfig() {
        if (this.getPsbConfig() == null || this.getPsbConfig().length() < 1) {
            return Optional.empty();
        }
        return Optional.of(JSONObject.parseObject(this.getPsbConfig()));
    }

    @Accessors(chain = true)
    @Getter
    @Setter
    public static class GuestInfo implements Serializable {

        /**
         * 单条公安上传Id
         */
        private String itemId;
        /**
         * 证件类型
         */
        private String certificateType;

        /**
         * 姓名
         */
        private String name;

        /**
         * 证件号码
         */
        private String credId;

        /**
         * 民族
         */
        private String nation;

        /**
         * 地址
         */
        private String address;

        /**
         * 照片
         */
        private String base64Img;

        /**
         * 生日
         */
        private String birthday;

        /**
         * 性别
         */
        private String sex;

        /**
         * 签发机构
         */
        private String signOffice;

        /**
         * 有效起始日期
         */
        private String usefulSDate;

        /**
         * 有效截止日期
         */
        private String usefulEDate;

        /**
         * 身份证附加消息
         */
        private String message;

        /**
         * 国家
         */
        private String country;

        /**
         * 省份
         */
        private String province;

        /**
         * 城市
         */
        private String city;

        /**
         * 电话
         */
        private String phone;

        /**
         * 入住日期 yyyy-MM-dd HH:mm:ss
         */
        private String checkInDate;

        /**
         * 离店日期 yyyy-MM-dd HH:mm:ss
         */
        private String checkOutDate;

        /**
         * 房间号
         */
        private String roomNum;

        /**
         * 实人认证会话唯一标识
         */
        private String verifyTicketId;

        /**
         * 人像比对相似度（0~1)
         */
        private String similarity;

        /**
         * 比对数据来源(默认阿里云)
         */
        private String verifySource = "Aliyun";

        /**
         * 现场照片(base64, 用于传奇门接口.)
         */
        private String sceneImg;

        /**
         * 现场图片大小 (默认100k)
         */
        private int sceneImgLength = 100;

    }

}
