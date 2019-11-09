<?php

namespace App\Http\Controllers\API;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\NguoiChoi;
class NguoiChoiController extends Controller
{
    public function getDS(Request $request)
    {
        return $request->all();
    }

    public function getDSNguoiChoi(Request $request){
        $page = $request->query('page',1);
        $limit = $request->query('limit',25);
        $danhSach = NguoiChoi::orderby('diem_cao_nhat','desc')->skip(($page-1)*$limit)->take($limit)->get();
        $result = ['success'=>true,
                'total'=>NguoiChoi::count(),
                'danh_sach'=>$danhSach
        ];
        return response()->json($result);
    }
}
