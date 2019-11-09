<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get('/nguoi_choi','API\NguoiChoiController@getDSNguoiChoi');

Route::prefix('/linh_vuc')->group(function(){
    Route::name('linh_vuc.')->group(function(){
         Route::post('/phan_trang','API\LinhVucController@getDanhSach')->name('danh_sach_phan_trang');
         Route::post('/them','API\LinhVucController@them')->name('them_moi');
         Route::post('/cap_nhat','API\LinhVucController@cap_nhat')->name('cap_nhat');
         Route::post('/xoa','API\LinhVucController@xoa')->name('xoa');
    });
 });

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});
