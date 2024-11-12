const listItems = document.querySelectorAll('.main__list__card');
const listLine = document.querySelectorAll('.a3');
const root = document.documentElement;
const colorRed = getComputedStyle(root).getPropertyValue('--bg-color-red-pastel');
const colorWhite = getComputedStyle(root).getPropertyValue('--white-color')

listItems.forEach(item => {
    const a1 = item.querySelector('.a1');
    const a2 = item.querySelectorAll('.a2');
    const a3 = item.querySelectorAll('.a3');
    const defaultImage = a1.style.backgroundImage;

    a2.forEach(e => {
        e.addEventListener('mouseenter', () => {
            const bgImage = e.getAttribute('data-bg');
            a1.style.backgroundImage = `url('${bgImage}')`;
            e.querySelector('.a3').style.backgroundColor = colorRed;
            a3.forEach(el => {
                el.style.visibility = 'visible';
             });
        });

        e.addEventListener('mouseleave', () => {
            a1.style.backgroundImage = defaultImage;
            e.querySelector('.a3').style.backgroundColor = colorWhite;
            a3.forEach(el => {
                el.style.visibility = 'hidden';
            });
        });
    });
});