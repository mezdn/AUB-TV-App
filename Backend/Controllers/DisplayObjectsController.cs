using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Backend.Data;
using Backend.Models;
using Microsoft.AspNetCore.Hosting;
using System.IO;
using Microsoft.AspNetCore.Authorization;

namespace Backend.Controllers
{
    [Authorize]
    public class DisplayObjectsController : Controller
    {
        private readonly TVContext _context;
        private readonly IWebHostEnvironment _env;

        public static List<string> Categories;

        public DisplayObjectsController(TVContext context, IWebHostEnvironment env)
        {
            _context = context;
            _env = env;
            Categories =  _context.Category.Select(c => c.Name).ToList();
        }

        // GET: DisplayObjects
        public async Task<IActionResult> Index(int id)
        {
            var category = _context.Category.FirstOrDefault(c => c.Id == id);
            if (category == null)
            {
                return NotFound();
            }

            return View(await _context.DisplayObject.Where(o => o.Category == category.Name).ToListAsync());
        }

        // GET: DisplayObjects/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var displayObject = await _context.DisplayObject
                .FirstOrDefaultAsync(m => m.Id == id);
            if (displayObject == null)
            {
                return NotFound();
            }

            return View(displayObject);
        }

        // GET: DisplayObjects/Create
        public IActionResult CreateVideo([FromRoute]int? categoryId)
        {
            return View();
        }

        // POST: DisplayObjects/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> CreateVideo([FromRoute]int? id, [Bind("Title,Description,YouTubeID,ImageUrls")] DisplayObject video)
        {
            if (ModelState.IsValid)
            {
                if (id == null)
                {
                    return ValidationProblem(detail: "Category Id cannot be null");
                }

                var category = await _context.Category.FirstOrDefaultAsync(c => c.Id == id);

                if (category == null)
                {
                    return NotFound("Category not found");
                }

                video.Type = Models.Interfaces.Type.Video;
                video.Category = category.Name;
                _context.Add(video);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index), new { id = id });
            }
            return View(video);
        }

        // GET: DisplayObjects/Create
        public IActionResult CreateSlideShow()
        {
            return View();
        }

        // POST: DisplayObjects/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> CreateSlideShow([FromRoute]int? id, [Bind("Title,Description,Pictures")] DisplayObject slideShow)
        {
            if (ModelState.IsValid && slideShow.Pictures != null)
            {

                if (id == null)
                {
                    return ValidationProblem(detail: "Category Id cannot be null");
                }

                var category = await _context.Category.FirstOrDefaultAsync(c => c.Id == id);

                if (category == null)
                {
                    return NotFound("Category not found");
                }

                slideShow.Type = Models.Interfaces.Type.SlideShow;
                slideShow.Category = category.Name;
                slideShow.ImageUrls = "";

                // Add unique name to avoid possible name conflicts
                var ImageFolderPath = Path.Combine(new string[] { _env.WebRootPath, "img" });
                if (!Directory.Exists(ImageFolderPath))
                {
                    Directory.CreateDirectory(ImageFolderPath);
                }

                slideShow.Pictures.Reverse();

                foreach (var pic in slideShow.Pictures)
                {
                    var uniqueImageName = DateTime.Now.Ticks.ToString() + ".jpg";
                    var ImageFilePath = Path.Combine(ImageFolderPath, uniqueImageName);
                    using (var fileStream = new FileStream(ImageFilePath, FileMode.Create, FileAccess.Write))
                    {
                        // Copy the photo to storage
                        await pic.CopyToAsync(fileStream);
                    }
                    slideShow.ImageUrls += @"/img/" + uniqueImageName + ';';
                }
                _context.Add(slideShow);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index), new { id = id });
            }
            return View(slideShow);
        }

        // GET: DisplayObjects/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var displayObject = await _context.DisplayObject.FindAsync(id);
            if (displayObject == null)
            {
                return NotFound();
            }
            return View(displayObject);
        }

        // POST: DisplayObjects/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Title,Description,Type")] DisplayObject displayObject)
        {
            if (id != displayObject.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(displayObject);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!DisplayObjectExists(displayObject.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(displayObject);
        }

        // GET: DisplayObjects/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var displayObject = await _context.DisplayObject
                .FirstOrDefaultAsync(m => m.Id == id);
            if (displayObject == null)
            {
                return NotFound();
            }

            return View(displayObject);
        }

        // POST: DisplayObjects/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var displayObject = await _context.DisplayObject.FindAsync(id);

            var category = await _context.Category.FirstOrDefaultAsync(c => c.Name == displayObject.Category);

            _context.DisplayObject.Remove(displayObject);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index), new { id = category.Id });
        }

        private bool DisplayObjectExists(int id)
        {
            return _context.DisplayObject.Any(e => e.Id == id);
        }
    }
}
