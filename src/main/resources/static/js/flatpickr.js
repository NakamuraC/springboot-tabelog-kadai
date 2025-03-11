let maxDate = new Date();
maxDate.setMonth(maxDate.getMonth() + 3);

// 予約日選択（カレンダー）
flatpickr('#fromReservationDate', {
    locale: 'ja',
    minDate: 'today',
    maxDate: maxDate,
    dateFormat: "Y-m-d",  // YYYY-MM-DD の形式で取得
    mode: "single"  // 単一日選択
});

// 予約時間選択（時間ピッカー）
flatpickr('#fromReservationTime', {
    enableTime: true,  // 時間選択を有効化
    noCalendar: true,  // カレンダーを無効化（時間のみ）
    dateFormat: "H:i", // HH:mm 形式（例: 14:30）
    time_24hr: true // 24時間表記
});