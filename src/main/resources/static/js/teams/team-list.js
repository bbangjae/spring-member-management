import { apiRequest } from '../api/request.js';
import { renderTable } from '../utils/tableRender.js';

async function fetchTeamsWithMemberCount() {
    const response = await apiRequest('/api/teams/member-count');
    return response.data;
}

function createTeamRowHtml(team, displayIndex) {
    return `
        <td>${displayIndex + 1}</td>
        <td class="member-team" data-team-id="${team.teamId}">${team.teamName}</td>
        <td class="member-count" data-member-count="${team.memberCount}">${team.memberCount}</td>
        <td><a href="/teams/${team.teamId}" class="details-link">⚙️</a></td>
    `;
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const teams = await fetchTeamsWithMemberCount();

        renderTable(
            'team-table-body',
            teams,
            createTeamRowHtml,
            '등록된 팀이 없습니다.'
        );
    } catch (error) {
        alert(error.message || '팀 목록을 불러오지 못했습니다.');
    }
});