<?php

namespace App\Http\Controllers\API;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\LinhVuc;
class LinhVucController extends Controller
{
    public function getDanhSach(Request $request){
        $page = $request->input('page');
        $limit = $request->input('limit');
        $danhSach = LinhVuc::orderBy('id','desc')->skip(($page-1)*$limit)->take($limit)->get();
        $result = [
            'success'=>true,
            'total'=>LinhVuc::count(),
            'danh_sach'=>$danhSach
        ];
        return response()->json($result);
    }
    
    public function them(Request $request){
        $linhVuc = new LinhVuc();
        $linhVuc->ten_linh_vuc = $request->input('ten_linh_vuc');
        $linhVuc->save();
        return "success";
    }

    public function xoa(Request $request){
        $linhVuc = LinhVuc::find($request->id);
        $linhVuc->delete();
        return "success";
    }
    public function cap_nhat(Request $request){
        $linhVuc = LinhVuc::find($request->id);
        $linhVuc->ten_linh_vuc = $request->ten_linh_vuc;
        $linhVuc->save();
        return "success";
    }
}
