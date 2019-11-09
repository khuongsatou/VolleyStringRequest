<?php

use Illuminate\Database\Seeder;
use App\NguoiChoi;
class NguoiChoiSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $count =1;
        while($count <= 300){
            NguoiChoi::create([
                'ten_dang_nhap'=>'ten'.$count,
                'mat_khau'=>'mat khau'.$count,
                'email'=>'khuong'.$count.'@gmail.com',
                'hinh_dai_dien'=>$count.'.png',
                'diem_cao_nhat'=>$count,
                'credit'=>$count+"00"
            ]);
            $count++;
        }
       
    }
}
