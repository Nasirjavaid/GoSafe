package com.twobvt.packetparcer

import java.text.SimpleDateFormat

 class PacketParser
{

companion object{
        //region "Update"
        var  group_id = ""
        var cluster_id = ""
        var proc_id = ""
        var device_id = ""
        var dev_type_id = ""
        var dev_model_id = 0
        var sim_no = ""
        var veh_id = 0
        var veh_reg_no = ""
        var veh_type = 0
        var cust_id = ""
        var rec_datetime  =""
        var gps_datetime =""
        var lat = ""
        var lng = ""
        var veh_status = ""
        var loc_status = ""
        var speed = ""
        var dir_angle = ""
        var ref_dist = ""
        var ref_location = ""
        var lm_id = ""
        var geo_status = ""
        var gps_status = ""
        var acc_status = ""
        var Online = ""
        var alarm_status = ""
        var mileage_last_noted = ""
        var mileage_cur_value = ""
        var dev_status = ""
        var fuel_level = ""
        var ad_tt1 = ""
        var ad_tt2 = ""
        var ad_tt3 = ""
        var rpm = ""
        var engin_temp = "";
        var fuel_consumed = ""
        var error_code = ""
        var error_desc = ""
        var gps_satelite = ""
        var gsm_signal = ""
        var h_v_dop = ""
        var recMode = ""
        var track_no = 0
        var ext_bat_voltage = ""
        var int_bat_voltage = ""
        var input_acc_data = ""
        var dev_sync = ""
        var mgt_sim_no = ""
        var group_trk_id = ""
        var driver = ""
        var wh_id = ""
        //endregion

        //region "Alarm"
        var almPriority =0
        var almSrNo =0
        var almRecDateTime = ""
        var almGpsDateTime = ""
        var alarmDesc = ""
        var alarmID = ""
        var almSpeed = ""
        var almLocation = ""
        //endregion

    fun packetParserFun( p_data :String ) : ParcerModel
    {

        var formatter = SimpleDateFormat("dd.MM.yyyy")
        var header : String  = ""
        var update : String = ""
        var alarms : String = ""

        var  i : Int  = 0
        var ArrData : Array<String>
        //_headInfo + "<D>" + _dPacket + "<D>" + _aPacket;

        ArrData  = p_data.split( "<D>"  , ignoreCase = true).toTypedArray()
        if (ArrData.size == 3)
        {
            header = ArrData[0];
            update = ArrData[1];
            alarms = ArrData[2];
        }

        i = 0
        if (update != "")
        {
           var ArrUpd : Array<String>  = update.split("<#>", ignoreCase = true).toTypedArray()
            if (ArrUpd.size > 51)
            {
                group_id  = ArrUpd[i++];
                cluster_id = ArrUpd[i++];
                proc_id = ArrUpd[i++];
                device_id = ArrUpd[i++];
                 dev_type_id = ArrUpd[i++];
                 dev_model_id = ArrUpd[i++].toInt()
                 sim_no = ArrUpd[i++];
                 veh_id = ArrUpd[i++].toInt()
                 veh_reg_no = ArrUpd[i++];
                 veh_type = ArrUpd[i++].toInt()
                 cust_id = ArrUpd[i++];
                 rec_datetime =ArrUpd[i++]
                 gps_datetime = ArrUpd[i++]
                 lat = ArrUpd[i++];
                 lng = ArrUpd[i++];
                 veh_status = ArrUpd[i++];
                 loc_status = ArrUpd[i++];
                 speed = ArrUpd[i++];
                 dir_angle = ArrUpd[i++];
                 ref_dist = ArrUpd[i++];
                 ref_location = ArrUpd[i++];
                 lm_id = ArrUpd[i++];
                 geo_status = ArrUpd[i++];
                 gps_status = ArrUpd[i++];
                 acc_status = ArrUpd[i++];
                 Online = ArrUpd[i++];
                 alarm_status = ArrUpd[i++];
                 mileage_last_noted = ArrUpd[i++];
                 mileage_cur_value = ArrUpd[i++];
                 dev_status = ArrUpd[i++];
                 fuel_level = ArrUpd[i++];
                 ad_tt1 = ArrUpd[i++];
                 ad_tt2 = ArrUpd[i++];
                 ad_tt3 = ArrUpd[i++];
                 rpm = ArrUpd[i++];
                 engin_temp = ArrUpd[i++];
                 fuel_consumed = ArrUpd[i++];
                 error_code = ArrUpd[i++];
                 error_desc = ArrUpd[i++];
                 gps_satelite = ArrUpd[i++];
                 gsm_signal = ArrUpd[i++];
                 h_v_dop = ArrUpd[i++];
                 recMode = ArrUpd[i++];
                 track_no = ArrUpd[i++].toInt()
                 ext_bat_voltage = ArrUpd[i++];
                 int_bat_voltage = ArrUpd[i++];
                 input_acc_data = ArrUpd[i++];
                 dev_sync = ArrUpd[i++];
                 mgt_sim_no = ArrUpd[i++];
                 group_trk_id = ArrUpd[i++];
                 driver = ArrUpd[i++];
                 wh_id = ArrUpd[i++];
                if (veh_id == 0)
                    veh_reg_no = device_id;
            }
        }

        i = 0;
       var  alarm_status  = "0"
        if (veh_id > 0)
        {
            if (alarms != "")
            {
                //_priority.ToString() + "<#>" + _srno.ToString() + "<#>" + _rDateTime.ToString(LIB.VAR.dtformate) + "<#>" + _gDateTime.ToString(LIB.VAR.dtformate) + "<#>" + alm_desc + "<#>" + _Speed + "<#>" + _Location + "<#>" + alm_id;
         var ArrAlm :Array<String> = alarms.split("<#>" , ignoreCase = true).toTypedArray()
                if (ArrAlm.size >= 8)
                {
                    almPriority = ArrAlm[i++].toInt()
                    almSrNo = ArrAlm[i++].toInt()
                    almRecDateTime = formatter.format(ArrAlm[i++])
                    almGpsDateTime = formatter.format(ArrAlm[i++])
                    alarmDesc = ArrAlm[i++];
                    almSpeed = ArrAlm[i++];
                    almLocation = ArrAlm[i++];
                    alarmID = ArrAlm[i++];
                   alarm_status = "1";
                }
            }
        }


        println("filtered data ::    $device_id,$alarmID , $rec_datetime  $gps_datetime")


        return ParcerModel(
             group_id,
         cluster_id ,
         proc_id ,
         device_id,
         dev_type_id ,
         dev_model_id ,
         sim_no ,
         veh_id ,
         veh_reg_no ,
         veh_type ,
         cust_id ,
         rec_datetime ,
         gps_datetime ,
         lat ,
         lng ,
         veh_status ,
         loc_status ,
         speed ,
         dir_angle ,
         ref_dist ,
         ref_location ,
         lm_id ,
         geo_status ,
         gps_status ,
         acc_status ,
         Online ,
         alarm_status ,
         mileage_last_noted ,
         mileage_cur_value ,
         dev_status ,
        fuel_level ,
         ad_tt1 ,
         ad_tt2 ,
         ad_tt3 ,
         rpm ,
         engin_temp ,
         fuel_consumed ,
         error_code ,
         error_desc ,
         gps_satelite ,
         gsm_signal ,
         h_v_dop ,
         recMode ,
         track_no ,
         ext_bat_voltage ,
         int_bat_voltage ,
         input_acc_data ,
         dev_sync ,
         mgt_sim_no ,
         group_trk_id ,
         driver ,
         wh_id ,
         almPriority ,
         almSrNo ,
         almRecDateTime ,
         almGpsDateTime ,
         alarmDesc ,
         alarmID ,
         almSpeed ,
         almLocation ,
        )
    }

//    //region "Update"
//    var  group_id = "";
//    var cluster_id = "";
//    var proc_id = "";
//    var device_id = "";
//    var dev_type_id = "";
//    var dev_model_id = 0;
//    var sim_no = "";
//    var veh_id = 0;
//    var veh_reg_no = "";
//    var veh_type = 0;
//    var cust_id = "";
//    var rec_datetime : LocalDateTime? = null
//    var gps_datetime : LocalDateTime? = null
//    var lat = "";
//    var lng = "";
//    var veh_status = "";
//    var loc_status = "";
//    var speed = "";
//    var dir_angle = "";
//    var ref_dist = "";
//    var ref_location = "";
//    var lm_id = "";
//    var geo_status = "";
//    var gps_status = "";
//    var acc_status = "";
//    var Online = "";
//    var alarm_status = "";
//    var mileage_last_noted = "";
//    var mileage_cur_value = "";
//    var dev_status = "";
//    var fuel_level = "";
//    var ad_tt1 = "";
//    var ad_tt2 = "";
//    var ad_tt3 = "";
//    var rpm = "";
//    var engin_temp = "";
//    var fuel_consumed = "";
//    var error_code = "";
//    var error_desc = "";
//    var gps_satelite = "";
//    var gsm_signal = "";
//    var h_v_dop = "";
//    var recMode = "";
//    var track_no = 0;
//    var ext_bat_voltage = "";
//    var int_bat_voltage = "";
//    var input_acc_data = "";
//    var dev_sync = "";
//    var mgt_sim_no = "";
//    var group_trk_id = "";
//    var driver = "";
//    var wh_id = "";
//    //endregion
//
//    //region "Alarm"
//    var almPriority =0;
//    var almSrNo =0;
//    var almRecDateTime : LocalDateTime? = null;
//    var almGpsDateTime : LocalDateTime? = null;
//    var alarmDesc = "";
//    var alarmID = "";
//    var almSpeed = "";
//    var almLocation = "";
//    //endregion
}}