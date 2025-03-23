import com.example.library.Book
import com.example.library.Disk
import com.example.library.Papper
import com.example.library.com.example.library.BookShop
import com.example.library.com.example.library.DiskShop
import com.example.library.com.example.library.PapperShop

class Manager {
    fun buyBook(shop: BookShop): Book {
        return shop.sale()
    }

    fun buyPapper(shop: PapperShop): Papper {
        return shop.sale()
    }

    fun buyDisk(shop: DiskShop): Disk {
        return shop.sale()
    }
}