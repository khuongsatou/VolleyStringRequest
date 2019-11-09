<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
class LinhVuc extends Model
{
    use softDeletes;
    protected $table = "linhvuc";
}
