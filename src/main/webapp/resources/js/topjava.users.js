const ajaxUrl = "ajax/admin/users/";
let datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function enableOrDisable(event) {
    let id = $(event.target).closest("tr").attr("id");
    let isActive = $(event.target).is(":checked");
    $.ajax({
        type: "POST",
        url: ajaxUrl + id,
        data: {
            "state": isActive
        }
    }).done(function (data) {
        updateTable();
    });
}