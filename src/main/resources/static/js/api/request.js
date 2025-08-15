export async function apiRequest(url, options = {}) {
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

