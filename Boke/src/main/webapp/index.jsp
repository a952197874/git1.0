<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <title>AES加密</title>
    <script src="/js/jquery.js"></script>
    <script src="/js/aes.js"></script>
    <script src="/js/crypto-js.js"></script>

    <script>
        //随机数生成算法,len生成结果的长度，radix进制
        function uuid(len,radix) {
            var chars="123456789QWERTYUIOPASDFGHJKLZXCVBNM".split('');
            var uuid=[],i;
            radix=radix||chars.length;
            if (len){
                for ( i = 0; i <len ; i++) {
                    uuid[i]=chars[0|Math.random()*radix];
                }
            } else {
                var r;
                uuid[8]=uuid[13]=uuid[18]=uuid[23]='-';
                uuid[14]='4';

                for (i = 0; i <36 ; i++) {
                    if (!uuid[i]){
                        r=0|Math.random()*16;
                        uuid[i]=chars[(i==19)?(r& 0x8):r];
                    }
                }

            }

            return uuid.join();
        }
        function encryptByAES(message,key) {
            var keyHex=CryptoJS.enc.Utf8.parse(key);
            var srcs=CryptoJS.enc.Utf8.parse(message);
            var encrypted=CryptoJS.AES.encrypt(srcs,keyHex,{
                mode:CryptoJS.mode.ECB,
                padding:CryptoJS.pad.Pkcs7
            });
            return encrypted.toString();
        }

        /**
         * 解密
         */
        function decryptByAES(encrypted,key) {
            var keyHex=CryptoJS.enc.Utf8.parse(key);
            var decrypt=CryptoJS.AES.decrypt(encrypted,keyHex,{
                mode:CryptoJS.mode.ECB,
                padding:CryptoJS.pad.Pkcs7
            });
            return CryptoJS.enc.Utf8.stringify(decrypt).toString();

        }
        function dopost() {
            var name=$("name").val();
            var password=$("password").val();
            var message=name+password;
            var key=uuid(16,16);
            var param={};
            param.name=name;
            param.password=password;
            param.key=key;
            //正确加密
            param.message=encryptByAES(message,key);
            $.ajax({
                'url':'/testaes',
                'data':param.message,
                'success':function(data) {
                    if(data){
                        alert("登陆成功!");
                    }else{
                        alert("服务器繁忙");
                    }
            }



            });



        }
    </script>
</head>
<body>
<input>
</body>
</html>
