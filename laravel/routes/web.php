<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/home','NguoiChoiController@index');
Route::post('/home','NguoiChoiController@upload');

Route::prefix('/linh_vuc')->group(function(){
   Route::name('linh_vuc.')->group(function(){
        Route::get('/','LinhVucController@index')->name('danh_sach');
        Route::post('/them','LinhVucController@create')->name('them_moi');
        Route::post('/xoa/{id}','LinhVucController@destroy')->name('xoa');
   });
});
Route::post('/linh_vuc','NguoiChoiController@index');

Route::get('/', function () {
    return view('welcome');
});
