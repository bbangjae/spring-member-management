function getFetchOptions(data) {
    return {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };
}

function getErrorMessage(responseBody, status) {
    if (status === 409) {
        return responseBody.statusMessage || '이미 존재하는 회원명입니다.';
    }
    return responseBody.statusMessage || '알 수 없는 오류';
}

async function registerMember(memberName) {
    try {
        const response = await fetch('/api/members', getFetchOptions({ memberName }));

        if (response.status === 204) {
            alert('회원 등록 성공!');
            window.location.href = '/';
            return;
        }

        const responseBody = await response.json();
        const msg = encodeURIComponent(getErrorMessage(responseBody, response.status));
        window.location.href = `/error/common?msg=${msg}`;
    } catch (error) {
        alert('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요');
        console.error('회원 등록 실패:', error);
    }
}

async function handleMemberFormSubmit(event) {
    event.preventDefault();

    const nameInput = document.getElementById('memberName');
    const memberName = nameInput.value.trim();

    await registerMember(memberName);
}

document.addEventListener('DOMContentLoaded', () => {
    const memberForm = document.getElementById('memberForm');
    if (!memberForm) return;

    memberForm.addEventListener('submit', handleMemberFormSubmit);
});