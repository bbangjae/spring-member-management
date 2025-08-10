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

async function registerMember(memberData) {
    try {
        const response = await fetch('/api/members', getFetchOptions(memberData));

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

async function loadTeams() {
    const teamSelect = document.getElementById('team');
    teamSelect.innerHTML = '<option>불러오는 중...</option>';

    try {
        const response = await fetch('/api/teams');
        if (!response.ok) throw new Error('팀 정보를 불러오는 데 실패했습니다.');

        const result = await response.json();
        const teams = result.data;
        if (!teams) throw new Error("API 응답 형식이 잘못되었습니다: 'data' 속성을 찾을 수 없습니다.");

        teamSelect.innerHTML = '<option value="">팀 선택</option>';
        teams.forEach(team => {
            const option = document.createElement('option');
            option.value = team.teamId;
            option.textContent = team.teamName;
            teamSelect.appendChild(option);
        });
    } catch (error) {
        teamSelect.innerHTML = '<option value="">팀 목록 불러오기 실패</option>';
        console.error('팀 로딩 실패:', error);
    }
}

async function handleMemberFormSubmit(event) {
    event.preventDefault();

    const teamIdValue = document.getElementById('team').value;
    const memberData = {
        memberName: document.getElementById('memberName').value.trim(),
        teamId: teamIdValue ? parseInt(teamIdValue, 10) : null,
        address: {
            city: document.getElementById('city').value.trim(),
            street: document.getElementById('street').value.trim(),
            zipcode: document.getElementById('zipcode').value.trim()
        }
    };
    await registerMember(memberData);
}

document.addEventListener('DOMContentLoaded', () => {
    const memberForm = document.getElementById('memberForm');
    if (memberForm) {
        memberForm.addEventListener('submit', handleMemberFormSubmit);

        loadTeams().catch(console.error);
    }

    const teamSelect = document.getElementById('team');
    if (teamSelect) {
        teamSelect.addEventListener('focus', () => {
            if (!teamsLoaded) loadTeams();
        }, { once: true });
    }
});