// Tham chiếu đến các phần tử HTML một lần, tái sử dụng
const favoriteMoviesContainer = document.getElementById('favoriteMoviesContainer');
const paginationContainer = document.getElementById('paginationContainer');
const deleteAllBtn = document.getElementById('deleteAllBtn');

/**
 * Hàm render danh sách phim yêu thích
 * @param {Array} favorites Mảng các favorite item
 */
const renderFavorites = (favorites) => {
    // Tạo HTML template cho danh sách
    let html = favorites.map(fav => {
        const movie = fav.movie;
        return `
      <div class="col-2">
        <a class="text-decoration-none text-dark" href="/phim/${movie.id}/${movie.slug}">
            <div class="movie-item">
              <div class="movie-poster rounded overflow-hidden">
                <img
                  src="${movie.thumbnail}"
                  alt="${movie.name}"
                >
              </div>
              <div class="movie-info d-flex justify-content-between align-items-center mt-3">
                <p class="mb-0">${movie.name}</p>
                <button
                  class="btn btn-sm btn-danger"
                  onclick="deleteFavorite(${movie.id})"
                >
                  Xóa
                </button>
              </div>
            </div>
        </a>
      </div>
    `;
    }).join('');

    favoriteMoviesContainer.innerHTML = html;
};

/**
 * Hàm render phân trang
 * @param {number} totalPages Tổng số trang
 * @param {number} currentPage Trang hiện tại (bắt đầu từ 1)
 */
const renderPagination = (totalPages, currentPage) => {
    // Nếu chỉ có 1 trang hoặc 0 trang, có thể ẩn đi cho gọn
    if (totalPages <= 1) {
        paginationContainer.innerHTML = '';
        return;
    }

    let paginationHTML = `
    <ul class="pagination justify-content-center">
      ${
        currentPage <= 1
            ? `<li class="page-item disabled"><button class="page-link" disabled>Previous</button></li>`
            : `<li class="page-item"><button class="page-link" onclick="getFavorites(${currentPage - 1})">Previous</button></li>`
    }
  `;

    for (let i = 1; i <= totalPages; i++) {
        paginationHTML += i === currentPage
            ? `<li class="page-item active"><button class="page-link">${i}</button></li>`
            : `<li class="page-item"><button class="page-link" onclick="getFavorites(${i})">${i}</button></li>`;
    }

    paginationHTML += `
      ${
        currentPage >= totalPages
            ? `<li class="page-item disabled"><button class="page-link" disabled>Next</button></li>`
            : `<li class="page-item"><button class="page-link" onclick="getFavorites(${currentPage + 1})">Next</button></li>`
    }
    </ul>
  `;

    paginationContainer.innerHTML = paginationHTML;
};

/**
 * Hàm gọi API để lấy danh sách phim yêu thích (có phân trang)
 * @param {number} page Trang muốn lấy (mặc định = 1)
 */
const getFavorites = async (page = 1) => {
    try {
        const pageSize = 12;
        // Gọi API GET /api/favorites
        const response = await axios.get('/api/favorites', {
            params: { page, pageSize }
        });
        // API giả sử trả về dạng { content, totalPages, number } (number là page index, 0-based)
        const data = response.data;
        const { content, totalPages, number } = data;

        // Nếu không có phim yêu thích
        if (!content || content.length === 0) {
            // Ẩn pagination + Ẩn nút xóa tất cả
            paginationContainer.innerHTML = '';
            deleteAllBtn.style.display = 'none';

            // Hiển thị thông báo
            favoriteMoviesContainer.innerHTML = `
        <div class="col-12">
          <p>Danh sách yêu thích trống.</p>
        </div>
      `;
            return;
        }

        // Có dữ liệu => hiển thị
        deleteAllBtn.style.display = 'inline-block'; // hoặc 'block' tùy ý
        renderFavorites(content);
        renderPagination(totalPages, number + 1); // number+1 để hiển thị trang hiện tại cho user

    } catch (error) {
        console.error(error);
        alert('Lỗi khi lấy danh sách phim yêu thích');
    }
};

/**
 * Hàm xóa 1 phim khỏi danh sách yêu thích
 * @param {number} movieId Id của phim
 */
const deleteFavorite = async (movieId) => {
    if (!window.confirm("Bạn có chắc muốn xóa phim này khỏi yêu thích?")) {
        return;
    }
    try {
        await axios.delete('/api/favorites/remove', {
            data: { movieId }
        });
        // Reload lại danh sách yêu thích ở trang 1 (hoặc trang hiện tại tùy ý)
        getFavorites(1);
    } catch (error) {
        console.error(error);
        alert('Lỗi khi xóa phim yêu thích');
    }
};

/**
 * Hàm xóa tất cả phim yêu thích
 */
const deleteAllFavorites = async () => {
    if (!window.confirm("Bạn có chắc muốn xóa TẤT CẢ phim khỏi yêu thích?")) {
        return;
    }
    try {
        await axios.delete('/api/favorites/removeAll');
        // Tải lại danh sách
        getFavorites(1);
    } catch (error) {
        console.error(error);
        alert('Lỗi khi xóa tất cả phim yêu thích');
    }
};

// Lần đầu load trang favorites
getFavorites(1);