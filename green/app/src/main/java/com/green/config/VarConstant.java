package com.green.config;

public class VarConstant {

    //user
    public static final String USER_TABLE = "user";
    public static final String USERID = "userId";
    public static final String USERNAME = "username";
    public static final String AVATAR = "avatar";
    public static final String PHONE = "phone";
    public static final String POINTS = "points";

    //activity
    public static final String ACTIVITY_TABLE = "activity";
    public static final String AID = "aId";
    public static final String ANAME = "aname";
    public static final String APLACE = "aplace";
    public static final String ANUM = "anum";
    public static final String ATIME = "atime";

    //eco
    public static final String ECO_TABLE = "eco";
    public static final String ECOID = "ecoId";
    public static final String ECONAME = "econame";
    public static final String ECOCONTENT = "ecocontent";
    public static final String ECOTIME = "ecotime";

    //pactivity关联表
    public static final String PACTIVITY_TABLE = "pactivity";
    public static final String PID = "pId";
    //复用上面ID
//    public static final String AID = "aId";
//    public static final String USERId = "userId";

    //medal
    public static final String MEDAL_TABLE = "medal";
    public static final String MID = "mId";
    public static final String MNAME = "mname";
    public static final String MIMG = "mImg";

    //rmedal关联表
    public static final String RID = "rId";
    //复用上面ID
//    public static final String MID = "mId";
//    public static final String USERID = "userId";

    //knowledge
    public static final String TABLE_KNOWLEDGE = "knowledge";
    public static final String KID = "kId";
    public static final String KNAME = "kName"; //知识标题
    public static final String KCONTENT = "kContent"; //知识内容

    //online
    public static final String TABLE_ONLINE = "online"; //线上活动公益表
    public static final String ONLINEID = "activityId"; //线上公益活动编号，主键，string类型
    public static final String ORGANIZATION = "organization"; //string类型
    public static final String REMAININGSLOTS = "remainingSlots"; //string类型
    public static final String ACTIVITYNAME = "activityName"; //string类型
    public static final String ODATE = "date"; //string类型，不用格式化为日期

    //favorite
    public static final String TABLE_FAVORITE = "favorite";
    public static final String FID = "fId";
    public static final String F_AID = "f_aId";
    public static final String F_USERID = "f_userId";

}
