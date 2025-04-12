const searchBox = document.getElementById('searchBox');
const suggestionsBox = document.getElementById('suggestionsBox');

searchBox.addEventListener('input', function () {
    const keyword = this.value;

    if (keyword.length >= 2) {
        fetch(`/employees/suggestions?term=${keyword}`)
            .then(res => res.json())
            .then(data => {
                suggestionsBox.innerHTML = '';
                data.forEach(item => {
                    const div = document.createElement('div');
                    div.textContent = item;
                    div.onclick = () => {
                        searchBox.value = item;
                        suggestionsBox.style.display = 'none';
                    };
                    suggestionsBox.appendChild(div);
                });
                suggestionsBox.style.display = data.length > 0 ? 'block' : 'none';
            });
    } else {
        suggestionsBox.style.display = 'none';
    }
});

document.addEventListener('click', (e) => {
    if (!e.target.closest('.autocomplete-wrapper')) {
        suggestionsBox.style.display = 'none';
    }
});
