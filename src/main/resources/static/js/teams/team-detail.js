import { apiRequest } from "../api/request.js";

const btnBar = document.getElementById('btnBar');
const toListBtn = document.getElementById("toListBtn");
const editBtn = document.getElementById("editBtn");
const saveBtn = document.getElementById("saveBtn");
const cancelBtn = document.getElementById("cancelBtn");
const deleteBtn = document.getElementById("deleteBtn");

let originalTeamName = '';

// 수정 버튼 클릭
editBtn.addEventListener("click", () => {
    const teamNameTd = document.getElementById("teamName");
    originalTeamName = teamNameTd.innerText;

    // input으로 변경
    teamNameTd.innerHTML = `<input type="text" id="editTeamName" class="form-control" value="${originalTeamName}">`;

    // 버튼 토글
    toListBtn.style.display = "none";
    editBtn.style.display = "none";
    deleteBtn.style.display = "none";
    saveBtn.style.display = "inline-block";
    cancelBtn.style.display = "inline-block";

    btnBar.classList.remove('justify-content-between');
    btnBar.classList.add('justify-content-center');
});

// 취소 버튼 클릭
cancelBtn.addEventListener("click", () => {
    // 원래 값으로 복원
    document.getElementById("teamName").innerText = originalTeamName;

    // 버튼 토글
    editBtn.style.display = "inline-block";
    toListBtn.style.display = "inline-block";
    deleteBtn.style.display = "inline-block";
    saveBtn.style.display = "none";
    cancelBtn.style.display = "none";

    btnBar.classList.remove('justify-content-center');
    btnBar.classList.add('justify-content-between');
});


// 저장 버튼 클릭
saveBtn.addEventListener("click", async () => {
    const teamId = window.location.pathname.split('/').pop();
    const newTeamName = document.getElementById("editTeamName").value;

    if (!newTeamName || newTeamName.trim() === '') {
        alert("팀 이름을 입력해주세요.");
        return;
    }

    try {
        await apiRequest(`/api/teams/${teamId}/edit`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ teamName: newTeamName })
            });

        alert("팀 이름이 성공적으로 수정되었습니다.");
        location.reload();
    } catch (error) {
        console.error("Error:", error);
    }
});

// 멤버 삭제 버튼에 대한 이벤트 리스너 추가
const memberDeleteButtons = document.querySelectorAll(".btn-danger[data-member-id]");
memberDeleteButtons.forEach(button => {
    button.addEventListener("click", async (event) => {
        const memberId = event.target.getAttribute("data-member-id");
        if (confirm("정말로 이 회원을 삭제하시겠습니까?")) {
            try {
                await apiRequest(`/api/members/${memberId}/delete`, {
                    method: "DELETE"
                });
                alert("회원이 삭제되었습니다.");
                location.load("/teams");
            } catch (e) {
                // apiRequest
            }
        }
    });
});

deleteBtn.addEventListener("click", async () => {
    if (confirm("정말로 이 팀을 삭제하시겠습니까?")) {
        const teamId = window.location.pathname.split('/').pop();
        try {
            await apiRequest(`/api/teams/${teamId}/delete`, {
                method: "DELETE"
            });
            alert("팀이 삭제되었습니다.");
            window.location.href = "/teams";
        } catch (e) {
            // apiRequest 내부에서 에러 처리됨
        }
    }
});
