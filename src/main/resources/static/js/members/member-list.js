async function apiRequest(url, options = {}) {
    try {
        const response = await fetch(url, options);
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({
                statusMessage: '일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.'
            }));
            throw new Error(errorData.statusMessage || '일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.');
        }
        if (response.status === 204) {
            return null;
        }
        return await response.json();
    } catch (error) {
        console.error('API 요청 실패:', error);
        throw error;
    }
}

async function fetchMembers() {
    const response = await apiRequest('/api/members');
    return response.data;
}

async function updateMemberName(memberId, newName) {
    await apiRequest(`/api/members/${memberId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            memberName: newName
        }),
    });
}

async function deleteMember(memberId) {
    await apiRequest(`/api/members/${memberId}`, {
        method: 'DELETE',
    });
}

function renderMembers(members) {
    const tbody = document.getElementById('member-table-body');
    tbody.innerHTML = '';

    if (!members || members.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" class="text-center">등록된 회원이 없습니다.</td></tr>';
        return;
    }

    members.forEach((member, index) => {
        const tr = document.createElement('tr');
        tr.dataset.memberId = member.memberId;
        tr.innerHTML = createMemberRowHtml(member, index);
        tbody.appendChild(tr);
    });
}

function createMemberRowHtml(member, displayIndex) {
    return `
        <td data-member-id="${member.memberId}"="${member.memberId}">${displayIndex + 1}</td>
        <td class="member-name" data-original-name="${member.memberName}">${member.memberName}</td>
        <td>
            <button class="btn btn-sm btn-outline-primary edit-btn">수정</button>
            <button class="btn btn-sm btn-outline-danger delete-btn">삭제</button>
        </td>
    `;
}

function switchToEditMode(row) {
    const nameCell = row.querySelector('.member-name');
    const originalName = nameCell.dataset.originalName;

    nameCell.innerHTML = `<input type="text" class="form-control form-control-sm" value="${originalName}">`;
    row.querySelector('.edit-btn').style.display = 'none';
    row.querySelector('.delete-btn').style.display = 'none';

    const actionsCell = row.querySelector('td:last-child');
    actionsCell.innerHTML += `
        <button class="btn btn-sm btn-success save-btn">저장</button>
        <button class="btn btn-sm btn-secondary cancel-btn">취소</button>
    `;
}

function switchToViewMode(row, newName) {
    const nameCell = row.querySelector('.member-name');
    const memberId = row.dataset.memberId;

    const updatedName = newName || nameCell.dataset.originalName;
    nameCell.textContent = updatedName;
    nameCell.dataset.originalName = updatedName; // 원래 이름 데이터 업데이트

    row.querySelector('.edit-btn').style.display = 'inline-block';
    row.querySelector('.delete-btn').style.display = 'inline-block';

    row.querySelector('.save-btn').remove();
    row.querySelector('.cancel-btn').remove();
}

function handleTableClick(event) {
    const target = event.target;
    const row = target.closest('tr');
    if (!row) return;

    const memberId = row.dataset.memberId;

    if (target.classList.contains('edit-btn')) {
        switchToEditMode(row);
    }

    if (target.classList.contains('save-btn')) {
        const input = row.querySelector('input[type="text"]');
        const newName = input.value.trim();
        const originalName = row.querySelector('.member-name').dataset.originalName;

        if (newName && newName !== originalName) {
            updateMemberName(memberId, newName)
                .then(() => {
                    switchToViewMode(row, newName);
                    alert('회원 정보가 성공적으로 수정되었습니다.');
                })
                .catch(error => {
                    alert(`오류: ${error.message}`);
                    switchToViewMode(row);
                });
        } else {
            switchToViewMode(row);
        }
    }

    if (target.classList.contains('cancel-btn')) {
        switchToViewMode(row);
    }

    if (target.classList.contains('delete-btn')) {
        if (confirm(`정말로 이 회원을 삭제하시겠습니까?`)) {
            deleteMember(memberId)
                .then(() => {
                    row.remove();
                    alert('회원이 삭제되었습니다.');
                })
                .catch(error => alert(`오류: ${error.message}`));
        }
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const members = await fetchMembers();
        renderMembers(members);

        const tbody = document.getElementById('member-table-body');
        tbody.addEventListener('click', handleTableClick);

    } catch (error) {
        alert(error.message || '회원 목록을 불러오지 못했습니다.');
    }
});