function showContent(type) {
    var panels = document.getElementsByClassName('content-panel');
    for (var i = 0; i < panels.length; i++) {
        panels[i].style.display = 'none';
        panels[i].classList.remove('active');
    }

    var tabButtons = document.getElementsByClassName('tab-button');
    for (var i = 0; i < tabButtons.length; i++) {
        tabButtons[i].classList.remove('active');
    }

    document.getElementById(type).style.display = 'block';
    document.getElementById(type).classList.add('active');

    if (type === 'coches') {
        tabButtons[0].classList.add('active');
    } else {
        tabButtons[1].classList.add('active');
    }
}
