import { apiRequest } from '../api/request.js';
import { renderTable } from '../utils/tableRender.js';

async function fetchMembers(page = 0, size = 10) {
    const response = await apiRequest(`/api/members?page=${page}&size=${size}`);
    return response.data; // Page<MemberWithTeamResponseDto>
}

function createMemberRowHtml(member, displayIndex) {
    return `
        <td>${displayIndex + 1}</td>
        <td class="member-name">${member.memberName}</td>
        <td class="member-team">${member.teamName}</td>
        <td><a href="/members/${member.memberId}" class="details-link">⚙️</a></td>
    `;
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const pageSize = 10;
        let currentPage = 0;

        async function loadPage(page) {
            const { content, totalPages, number } = await fetchMembers(page, pageSize);

            renderTable(
                'member-table-body',
                content,
                createMemberRowHtml,
                '등록된 회원이 없습니다.'
            );

            renderPagination(totalPages, number);
        }

        function renderPagination(totalPages, currentPage) {
            const pagination = document.getElementById('pagination');
            pagination.innerHTML = ''; // 초기화

            const ul = document.createElement('ul');
            ul.classList.add('pagination', 'justify-content-center', 'mt-3');

            for (let i = 0; i < totalPages; i++) {
                const li = document.createElement('li');
                li.classList.add('page-item');
                if (i === currentPage) {
                    li.classList.add('active');
                }

                const a = document.createElement('a');
                a.classList.add('page-link');
                a.innerText = "" + i + 1;
                a.href = '#';
                a.addEventListener('click', (e) => {
                    e.preventDefault();
                    loadPage(i);
                });

                li.appendChild(a);
                ul.appendChild(li);
            }
            pagination.appendChild(ul);
        }

        // 첫 페이지 로딩
        await loadPage(currentPage);

    } catch (error) {
        alert(error.message || '회원 목록을 불러오지 못했습니다.');
    }
});
