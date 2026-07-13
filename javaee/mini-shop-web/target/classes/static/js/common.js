const API = {
    async request(url, options = {}) {
        const res = await fetch(url, { credentials: 'include', ...options });
        if (res.status === 401) {
            window.location.href = '/login';
            return;
        }
        if (res.status === 403) {
            alert('您没有权限执行此操作');
            return { code: 403, message: '无权限访问' };
        }
        return res.json();
    },
    get(url) { return this.request(url); },
    post(url, data) {
        return this.request(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: data ? JSON.stringify(data) : undefined
        });
    },
    put(url, data) {
        return this.request(url, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: data ? JSON.stringify(data) : undefined
        });
    },
    del(url) { return this.request(url, { method: 'DELETE' }); }
};

function renderPagination(paginationId, pageResult, callback) {
    const el = document.getElementById(paginationId);
    if (!el || !pageResult) return;
    const { pageNum, pages, total, pageSize } = pageResult;
    let html = `<span>共 ${total} 条</span>`;
    html += `<button ${pageNum <= 1 ? 'disabled' : ''} onclick="${callback}(${pageNum - 1})">上一页</button>`;
    const start = Math.max(1, pageNum - 2);
    const end = Math.min(pages, pageNum + 2);
    for (let i = start; i <= end; i++) {
        html += `<button class="${i === pageNum ? 'active' : ''}" onclick="${callback}(${i})">${i}</button>`;
    }
    html += `<button ${pageNum >= pages ? 'disabled' : ''} onclick="${callback}(${pageNum + 1})">下一页</button>`;
    el.innerHTML = html;
}

function formatDateTime(dt) {
    if (!dt) return '-';
    return dt.replace('T', ' ').substring(0, 19);
}

function showModal(id) { document.getElementById(id).classList.add('show'); }
function hideModal(id) { document.getElementById(id).classList.remove('show'); }

function logout() {
    fetch('/api/auth/logout', { method: 'POST', credentials: 'include' }).then(() => {
        window.location.href = '/login';
    });
}
