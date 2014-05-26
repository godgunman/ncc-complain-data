//
//  DetailViewController.h
//  fakenews
//
//  Created by Golo on 14/5/27.
//  Copyright (c) 2014年 moskastudio. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DetailViewController : UIViewController

@property (strong, nonatomic) id detailItem;

@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@end
