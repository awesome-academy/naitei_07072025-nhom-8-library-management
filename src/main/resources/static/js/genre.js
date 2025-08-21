document.addEventListener('DOMContentLoaded', function () {
    const tree = document.querySelector('.tree-view');

    // Smooth toggle function
    function smoothToggle(childrenUl, isExpanding) {
        if (!childrenUl) return;

        if (isExpanding) {
            childrenUl.classList.remove('hidden');
            childrenUl.style.maxHeight = '0px';
            childrenUl.style.opacity = '0';
            childrenUl.offsetHeight; // Force reflow
            requestAnimationFrame(() => {
                childrenUl.style.maxHeight = childrenUl.scrollHeight + 'px';
                childrenUl.style.opacity = '1';
            });
            setTimeout(() => {
                if (!childrenUl.classList.contains('hidden')) {
                    childrenUl.style.maxHeight = '';
                }
            }, 300);
        } else {
            childrenUl.style.maxHeight = childrenUl.scrollHeight + 'px';
            childrenUl.style.opacity = '1';
            childrenUl.offsetHeight; // Force reflow
            requestAnimationFrame(() => {
                childrenUl.style.maxHeight = '0px';
                childrenUl.style.opacity = '0';
            });
            setTimeout(() => {
                if (childrenUl.style.maxHeight === '0px') {
                    childrenUl.classList.add('hidden');
                    childrenUl.style.maxHeight = '';
                    childrenUl.style.opacity = '';
                }
            }, 300);
        }
    }

    // Toggle with smooth animation
    tree.addEventListener('click', function (e) {
        const toggleBtn = e.target.closest('.toggle');
        if (!toggleBtn || !tree.contains(toggleBtn)) return;

        e.preventDefault();
        e.stopPropagation();

        const li = toggleBtn.closest('li');
        if (!li) return;

        const childrenUl = li.querySelector(':scope > ul');
        if (!childrenUl) return;

        const isCurrentlyCollapsed = li.classList.contains('collapsed');
        const isExpanding = isCurrentlyCollapsed;

        li.classList.add('loading');
        li.classList.toggle('collapsed');
        smoothToggle(childrenUl, isExpanding);
        setTimeout(() => {
            li.classList.remove('loading');
        }, 300);

        console.log(`Toggled node: ${li.querySelector('.name').textContent}, expanding: ${isExpanding}`);
    });

    // Keyboard support
    tree.addEventListener('keydown', function (e) {
        const toggleBtn = e.target.closest('.toggle');
        if (!toggleBtn) return;

        if (e.key === 'Enter' || e.key === ' ') {
            e.preventDefault();
            toggleBtn.click();
        }

        if (e.key === 'ArrowRight' || e.key === 'ArrowLeft') {
            e.preventDefault();
            const li = toggleBtn.closest('li');
            const isCollapsed = li.classList.contains('collapsed');

            if (e.key === 'ArrowRight' && isCollapsed) {
                toggleBtn.click();
            } else if (e.key === 'ArrowLeft' && !isCollapsed) {
                toggleBtn.click();
            }
        }
    });

    // Search functionality
    const searchInput = document.getElementById('genreSearch');
    if (searchInput) {
        let searchTimeout;

        searchInput.addEventListener('input', function () {
            const query = this.value.toLowerCase().trim();
            if (searchTimeout) clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => performSearch(query), 150);
        });

        function performSearch(query) {
            const allNodes = document.querySelectorAll('.tree-view li');

            if (query === '') {
                allNodes.forEach(li => {
                    li.style.display = '';
                    li.classList.remove('search-match');
                    if (!li.classList.contains('collapsed')) {
                        const childrenUl = li.querySelector(':scope > ul');
                        if (childrenUl && li.classList.contains('has-children')) {
                            li.classList.add('collapsed');
                            smoothToggle(childrenUl, false);
                        }
                    }
                });
                return;
            }

            const matchingNodes = [];
            allNodes.forEach(li => {
                const nameEl = li.querySelector('.tree-node .name');
                const name = nameEl ? nameEl.textContent.toLowerCase() : '';
                const isMatch = name.includes(query);

                li.classList.toggle('search-match', isMatch);
                if (isMatch) {
                    matchingNodes.push(li);
                    li.style.display = '';
                } else {
                    li.style.display = 'none';
                }
            });

            matchingNodes.forEach(matchedNode => {
                let parent = matchedNode.parentElement.closest('li');
                while (parent) {
                    parent.style.display = '';
                    if (parent.classList.contains('collapsed')) {
                        parent.classList.remove('collapsed');
                        const parentUl = parent.querySelector(':scope > ul');
                        if (parentUl) smoothToggle(parentUl, true);
                    }
                    parent = parent.parentElement.closest('li');
                }
            });

            matchingNodes.forEach((node, index) => {
                setTimeout(() => {
                    node.style.transform = 'scale(1.02)';
                    setTimeout(() => node.style.transform = '', 150);
                }, index * 50);
            });
        }
    }

    // Expand/collapse all
    function expandAll() {
        const collapsedNodes = document.querySelectorAll('.tree-view li.has-children.collapsed');
        collapsedNodes.forEach((li, index) => {
            setTimeout(() => {
                const childrenUl = li.querySelector(':scope > ul');
                if (childrenUl) {
                    li.classList.remove('collapsed');
                    smoothToggle(childrenUl, true);
                }
            }, index * 50);
        });
    }

    function collapseAll() {
        const expandedNodes = document.querySelectorAll('.tree-view li.has-children:not(.collapsed)');
        expandedNodes.forEach((li, index) => {
            setTimeout(() => {
                const childrenUl = li.querySelector(':scope > ul');
                if (childrenUl) {
                    li.classList.add('collapsed');
                    smoothToggle(childrenUl, false);
                }
            }, index * 50);
        });
    }

    window.expandAllGenres = expandAll;
    window.collapseAllGenres = collapseAll;

    // Tooltips
    if (typeof bootstrap !== 'undefined') {
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.forEach(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
    }

    // Edit buttons
    function setupEditButtons() {
        const editButtons = document.querySelectorAll('.edit-btn');
        editButtons.forEach(btn => {
            btn.removeEventListener('click', handleEditClick);
            btn.addEventListener('click', handleEditClick);
        });
    }

    function handleEditClick(e) {
        const btn = e.currentTarget;
        const id = btn.dataset.id;
        const name = btn.dataset.name;
        const parent = btn.dataset.parent || '';

        const form = document.getElementById('editGenreForm');
        form.action = `/admin/genres/${id}/edit`;
        document.getElementById('editGenreId').value = id;
        document.getElementById('editGenreName').value = name;
        document.getElementById('editParentGenre').value = parent;
    }

    // Delete buttons
    function setupDeleteButtons() {
        const deleteButtons = document.querySelectorAll('.delete-btn');
        const deleteModalEl = document.getElementById('deleteGenreModal');
        if (!deleteModalEl) return;
        const deleteModal = new bootstrap.Modal(deleteModalEl, { keyboard: true });
        const deleteForm = document.getElementById('deleteGenreForm');
        const genreNameSpan = document.getElementById('genreNameToDelete');

        deleteButtons.forEach(btn => {
            btn.removeEventListener('click', handleDeleteClick);
            btn.addEventListener('click', handleDeleteClick);
        });

        function handleDeleteClick(e) {
            const btn = e.currentTarget;
            const genreId = btn.dataset.id;
            const genreName = btn.dataset.name;

            if (genreNameSpan) genreNameSpan.textContent = genreName;
            if (deleteForm) deleteForm.action = `/admin/genres/${genreId}/delete`;
            deleteModal.show();
        }
    }

    // Init buttons
    setupEditButtons();
    setupDeleteButtons();

    // Re-setup after DOM changes
    const observer = new MutationObserver(() => {
        setupEditButtons();
        setupDeleteButtons();
    });
    observer.observe(tree, { childList: true, subtree: true });

    // Toast messages
    const toastEl = document.getElementById('toastMessage');
    if (toastEl) {
        const successMessage = document.querySelector('[data-success-message]')?.dataset.successMessage;
        const errorMessage = document.querySelector('[data-error-message]')?.dataset.errorMessage;

        if (successMessage || errorMessage) {
            const toastBody = document.getElementById('toastBody');
            toastBody.textContent = successMessage || errorMessage;
            if (errorMessage) {
                toastEl.classList.remove('bg-success');
                toastEl.classList.add('bg-danger');
            }
            const toast = new bootstrap.Toast(toastEl);
            toast.show();
        }
    }
});
