<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Up Ảnh</title>
</head>
<body>
    <form action="" method="POST" enctype="multipart/form-data">
        @csrf 
        <input name="hinh_dai_dien" type="file"/><br>
        <button>Upload</button>

    </form>
</body>
</html>