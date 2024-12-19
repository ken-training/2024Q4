document.addEventListener('DOMContentLoaded', function() {
    // セレクトボックスのリストを取得
    var selects = document.getElementsByName('status');

    // 各セレクトボックスにイベントリスナーを追加
    for (var i = 0; i < selects.length; i++) {
        selects[i].addEventListener('change', function() {
            // #text 要素を取得
            var textElement = document.getElementById('text');

            // すべてのクラスを削除
            this.className = "";

            // セレクトボックスの値に応じてクラスを追加
            switch (this.value) {  // `this` は現在のセレクトボックスを指します
                case '未発送':
                    this.classList.add('unship');
                    break;

                case '発送済':
                    this.classList.add('ship');
                    break;

                case '注文取消':
                    this.classList.add('delete');
                    break;
            }
        });
    }
});