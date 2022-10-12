class Pagination {
  constructor(page, rowsPerPage) {
    this.page = page ? page : 1;
    this.rowsPerPage = rowsPerPage ? rowsPerPage : 5;
    this.totalItems = 0;
    this.descending = false;
  }
}
export default Pagination;
