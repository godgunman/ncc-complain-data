//
//  OneChannelViewController.h
//  fakenews
//
//  Created by Golo on 14/5/28.
//  Copyright (c) 2014年 moskastudio. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OneChannelViewController : UIViewController<UICollectionViewDelegate,UICollectionViewDataSource>
@property NSMutableDictionary *selectedChannel;
@property int rank;
@property IBOutlet UIView *backButtonView;
@property IBOutlet UILabel *rankLable;
@property IBOutlet UIImageView *rankBackgroundImageView;
@property IBOutlet UILabel *nameLable;
@property IBOutlet UILabel *numberLable;

@property IBOutlet UICollectionView *categoryCollectionView;
@property NSMutableArray *categoryArray;

@end
