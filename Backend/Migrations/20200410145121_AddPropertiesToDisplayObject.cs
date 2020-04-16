using Microsoft.EntityFrameworkCore.Migrations;

namespace Backend.Migrations
{
    public partial class AddPropertiesToDisplayObject : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "ImageUrls",
                table: "DisplayObject",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "YouTubeID",
                table: "DisplayObject",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ImageUrls",
                table: "DisplayObject");

            migrationBuilder.DropColumn(
                name: "YouTubeID",
                table: "DisplayObject");
        }
    }
}
