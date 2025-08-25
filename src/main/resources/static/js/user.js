// Function to handle page size change
function changePageSize(size) {
    const url = new URL(window.location);
    url.searchParams.set('size', size);
    url.searchParams.set('page', '0'); // Reset to first page
    window.location.href = url.toString();
}

// Function to refresh the table
function refreshTable() {
    window.location.reload();
}

// Handle user actions after the DOM is fully loaded
document.addEventListener('DOMContentLoaded', function() {
    const actionModal = new bootstrap.Modal(document.getElementById('actionModal'));
    const actionForm = document.getElementById('actionForm');
    const actionModalLabel = document.getElementById('actionModalLabel');
    const actionMessage = document.getElementById('actionMessage');
    const actionWarningText = document.getElementById('actionWarningText');
    const submitActionButton = document.getElementById('submitActionButton');
    const actionButtonText = document.getElementById('actionButtonText');
    const actionButtonIcon = document.getElementById('actionButtonIcon');
    const httpMethodInput = document.getElementById('httpMethod');
    // Handle all action buttons
    document.querySelectorAll('.deactivate-btn, .reactivate-btn').forEach(button => {
        button.addEventListener('click', function() {
            const userId = this.getAttribute('data-id');
            const userName = this.getAttribute('data-name');
            const action = this.getAttribute('data-action');

            if (action === 'deactivate') {
                actionModalLabel.textContent = messages.deactivateTitle;
                actionMessage.textContent = `${messages.deactivateConfirm} ${userName}?`;
                actionWarningText.textContent = messages.deactivateWarning;
                submitActionButton.classList.remove('btn-success');
                submitActionButton.classList.add('btn-danger');
                actionButtonIcon.className = 'bi bi-person-x-fill';
                actionButtonText.textContent = messages.deactivateButton;
                actionForm.action = `/admin/users/${userId}/deactivate`;
                httpMethodInput.value = 'PUT';
            } else if (action === 'reactivate') {
                actionModalLabel.textContent = messages.reactivateTitle;
                actionMessage.textContent = `${messages.reactivateConfirm} ${userName}?`;
                actionWarningText.textContent = messages.reactivateWarning;
                submitActionButton.classList.remove('btn-danger');
                submitActionButton.classList.add('btn-success');
                actionButtonIcon.className = 'bi bi-person-check-fill';
                actionButtonText.textContent = messages.reactivateButton;
                actionForm.action = `/admin/users/${userId}/reactivate`;
                httpMethodInput.value = 'PUT';
            }
            actionModal.show();
        });
    });

    // Auto-submit search form with debounce
    let searchTimeout;
    document.getElementById('searchName')?.addEventListener('input', function() {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(() => {
            this.form.submit();
        }, 500);
    });

    // Handle sort change
    document.getElementById('sortBy')?.addEventListener('change', function() {
        this.form.submit();
    });
});
