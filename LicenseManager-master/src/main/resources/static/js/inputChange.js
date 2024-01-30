function inputChange(event) {

	//テキストボックスの入力値が1文字未満の場合
	if (event.currentTarget.value.length < 1) {

		//全選択のチェックボックスを表示
		document.getElementsByClassName('all-check-block')[0].classList.remove("hidden");

		//テキストボックスの入力値が1文字以上の場合
	} else {

		//全選択のチェックボックスを非表示
		document.getElementsByClassName('all-check-block')[0].classList.add("hidden");
	}

	//チェックボックス（資格）分繰り返し
	for (let i = 0; i < checks.length; i++) {

		//チェックボックス（資格）の値に入力値が含まれる
		if (~checks[i].value.toLowerCase().indexOf(event.currentTarget.value.toLowerCase())) {

			//チェックボックスを表示
			document.getElementById(checks[i].value).classList.remove("hidden");

			//チェックボックス（資格）の値に入力値が含まれない
		} else {

			//チェックボックスを非表示
			document.getElementById(checks[i].value).classList.add("hidden");
		}
	}
}

//#search-box（テキストボックス）を取得
let searchBox = document.getElementById('search-box');

//.normal-check（選択肢チェックボックス）を取得
let checks = document.getElementsByClassName('normal-check');

// #search-box（テキストボックス）入力時、inputChange()発火
searchBox.addEventListener('input', inputChange);