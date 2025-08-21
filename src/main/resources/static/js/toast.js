document.addEventListener("DOMContentLoaded", function () {
    const toastEl = document.getElementById('toastMessage');
    if (!toastEl) return;

    const toastBody = document.getElementById('toastBody');
    if (typeof bootstrap === 'undefined') return;

    const toast = new bootstrap.Toast(toastEl, {
        autohide: true,
        delay: 4000
    });

    // tìm các element chứa success/error message
    const successEl = document.querySelector('[data-success-message]');
    const errorEl = document.querySelector('[data-error-message]');

    // kiểm tra success message
    if (successEl) {
        const successMsg = successEl.getAttribute('data-success-message');
        if (successMsg && successMsg !== "null" && successMsg.trim() !== "" && !successMsg.includes('${')) {
            toastEl.classList.remove('bg-danger');
            toastEl.classList.add('bg-success');
            toastBody.textContent = successMsg;
            toast.show();
        }
    }

    // kiểm tra error message
    if (errorEl) {
        const errorMsg = errorEl.getAttribute('data-error-message');
        if (errorMsg && errorMsg !== "null" && errorMsg.trim() !== "" && !errorMsg.includes('${')) {
            toastEl.className = 'toast align-items-center text-white bg-danger border-0';
            toastBody.textContent = errorMsg;
            toast.show();
        }
    }
});
