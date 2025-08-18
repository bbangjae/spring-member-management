import { apiRequest } from "../api/request.js";

document.addEventListener("DOMContentLoaded", () => {
    const editBtn = document.getElementById("editBtn");
    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");
    const deleteBtn = document.getElementById("deleteBtn");
    const toListBtn = document.getElementById("toListBtn");
    const buttonGroupContainer = document.getElementById("button-group-container");

    const memberId = document.getElementById("memberId").innerText;
    let originalValues = {};

    /** 버튼 토글 */
    function toggleButtons(mode) {
        const isEdit = mode === "edit";
        editBtn.style.display = isEdit ? "none" : "inline-block";
        deleteBtn.style.display = isEdit ? "none" : "inline-block";
        toListBtn.style.display = isEdit ? "none" : "inline-block";
        saveBtn.style.display = isEdit ? "inline-block" : "none";
        cancelBtn.style.display = isEdit ? "inline-block" : "none";

        buttonGroupContainer.classList.toggle("justify-content-between", !isEdit);
        buttonGroupContainer.classList.toggle("justify-content-center", isEdit);
    }

    /** 수정 모드 진입 */
    editBtn.addEventListener("click", async () => {
        const addressEl = document.getElementById("address");

        originalValues = {
            memberName: document.getElementById("memberName").innerText.trim(),
            teamName: document.getElementById("teamName").innerText.trim(),
            address: addressEl.innerText.trim(),
            city: addressEl.dataset.city,
            street: addressEl.dataset.street,
            zipcode: addressEl.dataset.zipcode,
            lockerNumber: document.getElementById("lockerNumber").innerText.trim()
        };

        document.getElementById("memberName").innerHTML =
            `<input type="text" id="editName" class="form-control" value="${originalValues.memberName}">`;

        const teamTd = document.getElementById("teamName");
        teamTd.innerHTML = `<select id="editTeam" class="form-select"><option>팀 불러오는 중...</option></select>`;
        try {
            const result = await apiRequest("/api/teams");
            const teams = result.data ?? [];

            const teamSelect = document.getElementById("editTeam");
            teamSelect.innerHTML = `<option value="">팀 선택</option>`;

            teams.forEach(team => {
                const option = document.createElement("option");
                option.value = team.teamId;
                option.textContent = team.teamName;
                if (team.teamName === originalValues.teamName) {
                    option.selected = true;
                }
                teamSelect.appendChild(option);
            });
        } catch (e) {
            document.getElementById("editTeam").innerHTML = "<option value=''>팀 목록 로딩 실패</option>";
        }

        document.getElementById("address").innerHTML = `
            <input type="text" id="editCity" class="form-control mb-1" placeholder="도시" value="${originalValues.city}">
            <input type="text" id="editStreet" class="form-control mb-1" placeholder="도로명" value="${originalValues.street}">
            <input type="text" id="editZipcode" class="form-control" placeholder="우편번호" value="${originalValues.zipcode}">
        `;

        document.getElementById("lockerNumber").innerHTML =
            `<input type="text" id="editLockerNumber" class="form-control" value="${originalValues.lockerNumber === "락커 없음" ? "" : originalValues.lockerNumber}">`;

        toggleButtons("edit");
    });

    /** 취소 버튼 클릭 */
    cancelBtn.addEventListener("click", () => {
        document.getElementById("memberName").innerText = originalValues.memberName;
        document.getElementById("teamName").innerText = originalValues.teamName;
        document.getElementById("address").innerText = originalValues.address;
        document.getElementById("lockerNumber").innerText = originalValues.lockerNumber;
        toggleButtons("view");
    });

    /** 저장 버튼 클릭 */
    saveBtn.addEventListener("click", async () => {
        const data = {
            memberName: document.getElementById("editName")?.value,
            teamId: document.getElementById("editTeam")?.value || null,
            address: {
                city: document.getElementById("editCity")?.value,
                street: document.getElementById("editStreet")?.value,
                zipcode: document.getElementById("editZipcode")?.value
            },
            lockerNumber: document.getElementById("editLockerNumber")?.value
        };

        try {
            await apiRequest(`/api/members/${memberId}/edit`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });
            alert("수정이 완료되었습니다.");
            location.reload();
        } catch (e) {
            // apiRequest 내부에서 에러 처리됨
        }
    });

    /** 삭제 버튼 클릭 */
    deleteBtn.addEventListener("click", async () => {
        if (confirm("정말로 이 회원을 삭제하시겠습니까?")) {
            try {
                await apiRequest(`/api/members/${memberId}/delete`, {
                    method: "DELETE"
                });
                alert("회원이 삭제되었습니다.");
                window.location.href = "/members";
            } catch (e) {
                // apiRequest 내부에서 에러 처리됨
            }
        }
    });
});
