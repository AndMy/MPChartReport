package com.hmsg.hospitalreport.utils

data class UpdateUiState<T>(
    var isSuccess: Boolean = true,
    var data : T,
    var errorMsg: String = ""
)
data class UpdateExcelState<T,T1,T2,T3>(
    var isSuccess: Boolean = true,
    var data : T,
    var data1 : T1,
    var data2 : T2,
    var data3 : T3
)
data class UpdateExcelState1<T,T1,T2,T3,T4>(
    var isSuccess: Boolean = true,
    var data : T,
    var data1 : T1,
    var data2 : T2,
    var data3 : T3,
    var data4 : T4
)